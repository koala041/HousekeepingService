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
import org.springframework.util.ResourceUtils;
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
	 * 上传文件
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
			if (originalFilename == null || !originalFilename.contains(".")) {
				throw new EIException("文件名不合法");
			}
			originalFilename = URLDecoder.decode(originalFilename, "UTF-8");
			String fileExt = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

			// 构建上传目录路径
			File path = new File(URLDecoder.decode(ResourceUtils.getURL("classpath:static").getPath(), StandardCharsets.UTF_8.name()));
			if (!path.exists()) {
				path = new File("");
			}
			File uploadDir = new File(path, "upload");
			if (!uploadDir.exists() && !uploadDir.mkdirs()) {
				throw new EIException("上传目录创建失败");
			}

			// 生成唯一文件名（时间戳+随机数），避免并发冲突
			String fileName;
			if (StringUtils.isNotBlank(type) && type.contains("_template")) {
				fileName = type + "." + fileExt;
				File oldFile = new File(uploadDir, fileName);
				if (oldFile.exists()) {
					oldFile.delete(); // 删除旧模板文件
				}
			} else {
				fileName = System.currentTimeMillis() + "_" + new Random().nextInt(1000) + "." + fileExt;
			}

			File dest = new File(uploadDir, fileName);

			// 保存文件到目标位置
			file.transferTo(dest);

			/**
            * 如果使用idea或者eclipse重启项目，发现之前上传的图片或者文件丢失，将下面一行代码注释打开
            * 请将以下的"D:\\springcloud-alibaba\\springcloud-module-system\\src\\main\\resources\\static\\upload"替换成你本地项目的upload路径，
            * 并且项目路径不能存在中文、空格等特殊字符
            */
			//		FileUtils.copyFile(dest, new File("D:\\springcloud-alibaba\\springcloud-module-system\\src\\main\\resources\\static\\upload"+"/"+fileName)); /**修改了路径以后请将该行最前面的//注释去掉**/


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

			File resourceDir = new File(ResourceUtils.getURL("classpath:static").getPath());
			if (!resourceDir.exists()) {
				resourceDir = new File("");
			}

			File uploadDir = new File(resourceDir, "upload");
			if (!uploadDir.exists() && !uploadDir.mkdirs()) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}

			File file = new File(uploadDir, fileName);
			if (!file.exists()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDispositionFormData("attachment", fileName);

			byte[] fileBytes = FileUtils.readFileToByteArray(file);
			return new ResponseEntity<>(fileBytes, headers, HttpStatus.CREATED);
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
		File path = new File(ResourceUtils.getURL("classpath:static").getPath());
		File uploadDir = new File(path, "upload");

		File originalFile = new File(uploadDir, fileName);

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
			File resourceDir = new File(ResourceUtils.getURL("classpath:static").getPath());
			if (!resourceDir.exists()) {
				resourceDir = new File("");
			}

			File uploadDir = new File(resourceDir, "upload");
			if (!uploadDir.exists()) {
				return R.error("上传目录不存在");
			}

			File encryptedFile = new File(uploadDir, fileName);
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
