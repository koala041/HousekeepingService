package com.config;

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
				.excludePathPatterns("/static/**","/upload/**","/file/**");
		super.addInterceptors(registry);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**")
				.addResourceLocations("classpath:/resources/")
				.addResourceLocations("classpath:/static/")
				.addResourceLocations("classpath:/public/");

		registry.addResourceHandler("/admin/**").addResourceLocations("classpath:/admin/");
		registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
		registry.addResourceHandler("/front-pc/**").addResourceLocations("classpath:/front-pc/");

		registry.addResourceHandler("/springcloud-alibaba3krib874/upload/**")
				.addResourceLocations("file:D:/upload/");
		registry.addResourceHandler("/upload/**")
				.addResourceLocations("classpath:/static/upload/");

		super.addResourceHandlers(registry);
	}
}