package com.anson.school_books_system.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author 
 * @package com.anson.school_books_system.config
 * @createTime
 * @description
 */
@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix = "school-books-system.config")
public class SchoolBookSysConfig {

    /**
     * 超級管理員帳號和密碼
     */
    private SuperAdmin superAdmin;

    @Data
    static class SuperAdmin {
        private String username;
        private String password;
    }
}
