package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.annotation.EnableCustomConfig;
import com.annotation.EnableRyFeignClients;

/**
 * 系统模块springcloud-module-system
 *
 * @author abo
 */
@EnableCustomConfig
@EnableRyFeignClients
@SpringBootApplication
// springboot启动
public class SystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
        System.out.println("系统模块启动成功");
    }
}
