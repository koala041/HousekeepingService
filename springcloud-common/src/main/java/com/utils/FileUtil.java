package com.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
/**
 * 类说明 :
 */
@Slf4j
public class FileUtil {
	public static byte[] FileToByte(File file) throws IOException {
		// 将数据转为流
		@SuppressWarnings("resource")
		InputStream content = new FileInputStream(file);
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		byte[] buff = new byte[100];
		int rc = 0;
		while ((rc = content.read(buff, 0, 100)) > 0) {
			swapStream.write(buff, 0, rc);
		}
		// 获得二进制数组
		return swapStream.toByteArray();
	}

	/**
	 * 获取类路径下static的目录
	 */
	public static String getClassPathStatic() {
		try {
			return new File(URLDecoder.decode(ResourceUtils.getURL("classpath:static").getPath(), StandardCharsets.UTF_8.name())).getPath();
		} catch (Exception e) {
			log.error("获取类路径失败", e);
			return "";
		}
	}

	/**
	 * 获取类路径的目录
	 */
	public static String getClassPath() {
		try {
			return new File(URLDecoder.decode(ResourceUtils.getURL("classpath:").getPath(), StandardCharsets.UTF_8.name())).getPath();
		} catch (Exception e) {
			log.error("获取类路径失败", e);
			return "";
		}
	}

	/**
	 * 获取上传目录
	 */
	public static String getUploadDir() {
		String classPath = getClassPathStatic();
		String uploadDir = classPath + "/upload/";
		ensureDirectoryExists(uploadDir);
		return uploadDir;
	}

	/**
	 * 获取输出目录
	 */
	public static String getOutputDir() {
		String classPath = getClassPathStatic();
		String outputDir = classPath + "/output/";
		ensureDirectoryExists(outputDir);
		return outputDir;
	}

	/**
	 * 确保目录存在
	 */
	public static void ensureDirectoryExists(String dirPath) {
		try {
			File dir = new File(dirPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
		} catch (Exception e) {
			log.error("创建目录失败: {}", dirPath, e);
		}
	}

	/**
	 * 保存上传的文件
	 */
	public static String saveUploadFile(byte[] fileData, String fileName) throws IOException {
		String uploadDir = getUploadDir();
		String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
		Path filePath = Paths.get(uploadDir, uniqueFileName);
		Files.write(filePath, fileData);
		return uniqueFileName;
	}

	/**
	 * 删除文件
	 */
	public static boolean deleteFile(String filePath) {
		try {
			File file = new File(filePath);
			return file.delete();
		} catch (Exception e) {
			log.error("删除文件失败: {}", filePath, e);
			return false;
		}
	}

	public static File getFile(String fileName) {
		String uploadDir = getUploadDir();
		return new File(uploadDir, fileName);
	}

	/**
	 * 获取文件扩展名
	 */
	public static String getFileExtension(String fileName) {
		if (fileName == null || !fileName.contains(".")) {
			return "";
		}
		return fileName.substring(fileName.lastIndexOf(".") ).toLowerCase();
	}

	/**
	 * 检查是否为图片文件
	 */
	public static boolean isImageFile(String fileName) {
		if (fileName == null) return false;
		String ext = getFileExtension(fileName);
		return ext.equals("jpg") || ext.equals("jpeg") || ext.equals("png");
	}

	/**
	 * 检查是否为视频文件
	 */
	public static boolean isVideoFile(String fileName) {
		if (fileName == null) return false;
		String ext = getFileExtension(fileName);
		return ext.equals("mp4") || ext.equals("avi") || ext.equals("mov");
	}

}
