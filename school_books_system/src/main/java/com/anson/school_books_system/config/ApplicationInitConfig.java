package com.anson.school_books_system.config;


import com.anson.school_books_system.service.AdminService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author
 * @package com.anson.school_books_system.config
 * @createTime 2024/3/11 - 14:29
 * @description
 */
@Order(100)
@Slf4j
@Component
@AllArgsConstructor
public class ApplicationInitConfig implements ApplicationRunner {

    private final AdminService adminService;
    private final SchoolBookSysConfig schoolBookSysConfig;

    /**
     * 系統啟動後，對系統進行初始化
     * 1、超級管理員帳號初始化
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        SchoolBookSysConfig.SuperAdmin superAdmin = schoolBookSysConfig.getSuperAdmin();
        if (Objects.nonNull(superAdmin)) {
            if (StringUtils.isNoneBlank(superAdmin.getUsername(), superAdmin.getPassword())) {
                try {
                    adminService.saveSuperAdmin(superAdmin.getUsername(), superAdmin.getPassword());
                } catch (Exception e) {
                    log.error("超級管理員帳號初始化失敗：", e);
                }
            }
        }
    }
}

