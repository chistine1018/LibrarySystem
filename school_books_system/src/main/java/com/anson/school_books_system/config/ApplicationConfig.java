package com.anson.school_books_system.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.anson.*.mapper")
public class ApplicationConfig {
}
