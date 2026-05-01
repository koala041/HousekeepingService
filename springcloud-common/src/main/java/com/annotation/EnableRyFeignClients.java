package com.annotation;

import com.feign.FeignAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.lang.annotation.*;

/**
 * 自定义feign注解
 * 添加basePackages路径
 *
 * @author abo
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableFeignClients
public @interface EnableRyFeignClients {
    String[] value() default {};
    
    String[] basePackages() default {"com"};
    
    Class<?>[] basePackageClasses() default {};
    
    Class<?>[] defaultConfiguration() default {FeignAutoConfiguration.class};
    
    Class<?>[] clients() default {};
}
