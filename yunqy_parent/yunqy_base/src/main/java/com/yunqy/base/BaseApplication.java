package com.yunqy.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import utils.DateWorker;
import utils.IdWorker;
import utils.JwtUtil;

/**
 * 基础模块的启动类
 */
@SpringBootApplication
public class BaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class,args);
    }

    /**
     * 把配置写在启动类里,通过代码的方式写配置
     * @return
     * @Bean  : 项目启动的初始化,注入的时候,直接注入就行,和写配置文件一样
     */
    @Bean
    public DateWorker dateWorkerate() {
        return new DateWorker();
    }

    /**
     * 注入生成id的工具类
     * @return
     */
    @Bean
    public IdWorker idWorker() {
        return new IdWorker(1,1);
    }

    /**
     * 注入安全框架的加密类
     * @return
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 生产token的bean
     * @return
     */
    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }
}
