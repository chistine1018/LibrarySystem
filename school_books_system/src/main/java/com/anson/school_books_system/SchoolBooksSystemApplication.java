package com.anson.school_books_system;

import com.anson.school_books_system.utils.ImgFileUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class SchoolBooksSystemApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext app = SpringApplication.run(SchoolBooksSystemApplication.class, args);
        // 啟動 springboot 後，獲取一下端口並設置道 ImgFileUtil 中
        Environment env = app.getEnvironment();
        String port = env.getProperty("server.port");
        if (StringUtils.isBlank(port)) {
            port = "8080";
        }
        ImgFileUtil.setPORT(Integer.valueOf(port));
    }
}
