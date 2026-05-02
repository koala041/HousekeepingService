package com.controller;

import com.annotation.IgnoreAuth;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.entity.ConfigEntity;
import com.entity.EIException;
import com.service.ConfigService;
import com.utils.EncryptUtil;
import com.utils.R;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Random;

/**
 * 上传文件映射表
 */
@RestController
@RequestMapping("file")
public class FileController {

	@Autowired
	private ConfigService configService;

	/**
	 * 定位当前 springcloud-module-system 模块的根目录
	 */
	private File findModuleRoot() throws IOException {
		try {
			// 获取当前类的编译输出目录（target/classes）
			File currentFile = new File(FileController.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			while (currentFile != null) {
				// 向上查找，直到找到 springcloud-module-system 模块的根目录
				if (new File(currentFile, "pom.xml").exists()
						&& new File(currentFile, "src/main").exists()) {
					return currentFile;
				}
				currentFile = currentFile.getParentFile();
			}
			throw new IOException("无法找到 springcloud-module-system 模块根目录");
		} catch (java.net.URISyntaxException e) {
			throw new IOException("获取项目路径失败", e);
		}
	}

	/**
	 * 获取上传目录：springcloud-module-system/upload
	 */
	private File getUploadDir() throws IOException {
		File moduleRoot = findModuleRoot();
		// 直接定位到模块根目录下的 upload 文件夹
		File uploadDir = new File(moduleRoot, "upload").getCanonicalFile();
		if (!uploadDir.exists() && !uploadDir.mkdirs()) {
			throw new IOException("上传目录创建失败：" + uploadDir.getAbsolutePath());
		}
		return uploadDir;
	}

	/**
	 * 安全解析文件路径，防止路径穿越攻击
	 */
	private File resolveUploadFile(String fileName) throws IOException {
		Path normalizedFileName = Paths.get(fileName).normalize();
		if (normalizedFileName.getParent() != null) {
			throw new SecurityException("非法文件路径");
		}
		return new File(getUploadDir(), normalizedFileName.toString()).getCanonicalFile();
	}

	/**
	 * 上传文件（仅保存到 upload 目录）
	 */
	@RequestMapping("/upload")
	@IgnoreAuth
	public R upload(@RequestParam("file") MultipartFile file, String type) {
		try {
			// 校验文件是否为空
			if (file.isEmpty()) {
				throw new EIException("上传文件不能为空");
			}

			// 获取原始文件名并解码处理（解决中文乱码）
			String originalFilename = file.getOriginalFilename();
			System.out.println("上传的图片文件：" + originalFilename);
			if (originalFilename == null || !originalFilename.contains(".")) {
				throw new EIException("文件名不合法");
			}
			originalFilename = URLDecoder.decode(originalFilename, "UTF-8");
			String fileExt = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

			File uploadDir = getUploadDir();

			// 生成唯一文件名（时间戳+随机数），避免并发冲突
			System.out.println("fileExt：" + fileExt);
			String fileName;
			if (StringUtils.isNotBlank(type) && type.contains("_template")) {
				fileName = type + "." + fileExt;
				File oldFile = new File(uploadDir, fileName);
				if (oldFile.exists()) {
					oldFile.delete();
				}
			} else {
				fileName = System.currentTimeMillis() + "_" + new Random().nextInt(1000) + "." + fileExt;
			}
			System.out.println("fileName: " + fileName);
			File dest = new File(uploadDir, fileName);

			// 保存文件到目标位置
			file.transferTo(dest);

			// 特殊业务逻辑：更新配置
			if (StringUtils.isNotBlank(type) && type.equals("1")) {
				ConfigEntity configEntity = configService.selectOne(new EntityWrapper<ConfigEntity>().eq("name", "faceFile"));
				if (configEntity == null) {
					configEntity = new ConfigEntity();
					configEntity.setName("faceFile");
				}
				configEntity.setValue(fileName);
				configService.insertOrUpdate(configEntity);
			}

			return R.ok().put("file", fileName);
		} catch (IOException e) {
			e.printStackTrace();
			return R.error("文件上传失败：" + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return R.error("系统异常，请检查配置或联系管理员");
		}
	}

	/**
	 * 下载文件
	 */
	@IgnoreAuth
	@RequestMapping("/download")
	public ResponseEntity<byte[]> download(@RequestParam String fileName) {
		if (fileName == null || fileName.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		try {
			// 规范化文件名，防止路径穿越攻击
			Path normalizedFileName = Paths.get(fileName).normalize();
			if (normalizedFileName.getParent() != null) {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}

			File file = resolveUploadFile(fileName);
			if (!file.exists()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDispositionFormData("attachment", fileName);

			byte[] fileBytes = FileUtils.readFileToByteArray(file);
			return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
		} catch (SecurityException e) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		} catch (IOException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 加密上传文件：支持任意文件（包括二进制）
	 */
	@RequestMapping("/encrypt")
	@IgnoreAuth
	public R encrypt(@RequestParam String fileName, @RequestParam String type) throws IOException {
		File uploadDir = getUploadDir();
		File originalFile = resolveUploadFile(fileName);

		// 路径校验
		if (!originalFile.exists() || !originalFile.getParentFile().equals(uploadDir)) {
			return R.error("非法文件路径或文件不存在");
		}

		// 构造新文件名
		String baseName = fileName.substring(0, fileName.lastIndexOf("."));
		String extension = fileName.substring(fileName.lastIndexOf("."));
		String encryptedFileName = baseName + "_encrypt" + extension;
		File encryptedFile = new File(uploadDir, encryptedFileName);

		try (InputStream is = new FileInputStream(originalFile); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

			byte[] buffer = new byte[4096];
			int len;
			while ((len = is.read(buffer)) > 0) {
				baos.write(buffer, 0, len);
			}

			// 加密原始字节
			byte[] encryptedBytes = new byte[0];
			switch (type.toLowerCase()) {
				case "des":
					encryptedBytes = EncryptUtil.desEncrypt(baos.toByteArray());
					break;
				case "aes":
					encryptedBytes = EncryptUtil.aesEncrypt(baos.toByteArray());
					break;
				default:
					return R.error("不支持的加密类型");
			}

			// 写入加密后的 Base64 字符串（便于存储）
			FileUtils.write(encryptedFile, Base64.getEncoder().encodeToString(encryptedBytes), StandardCharsets.UTF_8);

		} catch (Exception e) {
			e.printStackTrace();
			return R.error("加密失败：" + e.getMessage());
		}
		// 删除原文件（可选）
		//originalFile.delete();
		return R.ok().put("file", encryptedFileName);
	}


	/**
	 * 解密文件并返回原始格式（支持非文本文件）
	 */
	@RequestMapping("/decrypt")
	@IgnoreAuth
	public R decrypt(@RequestParam String fileName, @RequestParam String type) {
		if (fileName == null || fileName.isEmpty()) {
			return R.error("文件名不能为空");
		}

		// 检查是否为加密文件（必须包含 _encrypt 标识）
		if (!fileName.contains("_encrypt")) {
			return R.error("只能解密带有 '_encrypt' 标识的文件");
		}

		try {
			File uploadDir = getUploadDir();
			File encryptedFile = resolveUploadFile(fileName);
			if (!encryptedFile.exists()) {
				return R.error("文件不存在");
			}

			// 读取加密内容为字符串
			String encryptedBase64 = FileUtils.readFileToString(encryptedFile, StandardCharsets.UTF_8);

			// Base64 解码成加密字节数组
			byte[] cipherBytes = Base64.getDecoder().decode(encryptedBase64);

			// 使用 AES 解密得到原始字节
			byte[] decryptedBytes = new byte[0];

			switch (type.toLowerCase()) {
				case "des":
					decryptedBytes = EncryptUtil.desDecrypt(cipherBytes);
					break;
				case "aes":
					decryptedBytes = EncryptUtil.aesDecrypt(cipherBytes);
					break;
				default:
					return R.error("不支持的解密类型");
			}


			// 构造解密后的文件名
			String decryptedFileName = fileName.replace("_encrypt", "");
			File decryptedFile = new File(uploadDir, decryptedFileName);

			// 写入原始字节流（避免 UTF-8 编码干扰）
			try (FileOutputStream fos = new FileOutputStream(decryptedFile)) {
				fos.write(decryptedBytes);
			}

			// （可选）删除加密文件
			//encryptedFile.delete();

			// 返回解密后的文件名
			return R.ok().put("file", decryptedFileName);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error("文件解密失败：" + e.getMessage());
		}
	}

}