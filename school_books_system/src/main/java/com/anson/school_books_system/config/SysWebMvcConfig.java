package com.anson.school_books_system.config;

import com.anson.school_books_system.interceptor.SecurityInterceptor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
@AllArgsConstructor
public class SysWebMvcConfig implements WebMvcConfigurer {

    private final SecurityInterceptor securityInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加安全認證攔截器，排除特定url
        registry.addInterceptor(securityInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/**/open-api/**/",
                        "/**/auth/login",
                        "/**/user/register",
                        "/**/test/**/"
                );
    }
}
