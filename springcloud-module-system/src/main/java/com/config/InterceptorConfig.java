package com.config;

import java.io.File;
import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.interceptor.AuthorizationInterceptor;

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport{

	@Bean
	public AuthorizationInterceptor getAuthorizationInterceptor() {
		return new AuthorizationInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(getAuthorizationInterceptor())
				.addPathPatterns("/**")
				.excludePathPatterns("/static/**","/upload/**","/file/**",
						"/*.jpg","/*.jpeg","/*.png","/*.gif","/*.bmp","/*.webp","/*.svg",
						"/*.mp4","/*.mp3","/*.wav","/*.ogg",
						"/**/*.jpg","/**/*.jpeg","/**/*.png","/**/*.gif","/**/*.bmp","/**/*.webp","/**/*.svg",
						"/**/*.mp4","/**/*.mp3","/**/*.wav","/**/*.ogg");
		super.addInterceptors(registry);
	}

	private File findModuleRoot() throws IOException {
		try {
			File currentFile = new File(InterceptorConfig.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			while (currentFile != null) {
				if (new File(currentFile, "pom.xml").exists()
						&& new File(currentFile, "src/main").exists()) {
					return currentFile;
				}
				currentFile = currentFile.getParentFile();
			}
			throw new IOException("无法找到 springcloud-module-system 模块根目录");
		} catch (Exception e) {
			throw new IOException("获取项目路径失败", e);
		}
	}
	
	private String getUploadPath() {
		try {
			File moduleRoot = findModuleRoot();
			File uploadDir = new File(moduleRoot, "upload").getCanonicalFile();
			return uploadDir.getAbsolutePath();
		} catch (IOException e) {
			return System.getProperty("user.home") + "/springcloud-alibaba/upload";
		}
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String uploadPath = getUploadPath();
		String uploadLocation = new File(uploadPath).toURI().toString();

		registry.addResourceHandler("/**")
				.addResourceLocations("classpath:/resources/")
				.addResourceLocations("classpath:/static/")
				.addResourceLocations("classpath:/public/")
				.addResourceLocations(uploadLocation)
				.addResourceLocations("classpath:/static/upload/");

		registry.addResourceHandler("/admin/**").addResourceLocations("classpath:/admin/");
		registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
		registry.addResourceHandler("/front-pc/**").addResourceLocations("classpath:/front-pc/");

		registry.addResourceHandler("/springcloud-alibaba/upload/**")
				.addResourceLocations(uploadLocation)
				.addResourceLocations("classpath:/static/upload/");
		registry.addResourceHandler("/upload/**")
				.addResourceLocations(uploadLocation)
				.addResourceLocations("classpath:/static/upload/");

		super.addResourceHandlers(registry);
	}
}
