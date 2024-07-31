package com.anson.school_books_system.handler;


import com.anson.school_books_system.enums.RoleEnum;
import com.anson.school_books_system.utils.SecurityUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Anson（about：）
 * @package com.anson.school_books_system.handler
 * @createTime
 * @description
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        // 起始版本 3.3.3(推薦)
        this.strictInsertFill(metaObject, "createTime", LocalDateTime::now, LocalDateTime.class);
        // 起始版本 3.3.3(推薦)
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
        if (Objects.nonNull(SecurityUtil.getAuth())) {
            String name = "";
            if (Boolean.FALSE.equals(RoleEnum.ROLE_USER.equals(SecurityUtil.getAuth().getRole()))) {
                name = SecurityUtil.getAuth().getAdminVO().getNickName();
            } else {
                name = SecurityUtil.getAuth().getUserVO().getName();
            }
            String finalName = name;
            this.strictInsertFill(metaObject, "creator", () -> finalName, String.class);
            this.strictInsertFill(metaObject, "modifier", () -> finalName, String.class);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 起始版本 3.3.3(推薦)
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
        if (Objects.nonNull(SecurityUtil.getAuth())) {
            String name = "";
            if (Boolean.FALSE.equals(RoleEnum.ROLE_USER.equals(SecurityUtil.getAuth().getRole()))) {
                name = SecurityUtil.getAuth().getAdminVO().getNickName();
            } else {
                name = SecurityUtil.getAuth().getUserVO().getName();
            }
            String finalName = name;
            this.strictInsertFill(metaObject, "modifier", () -> finalName, String.class);
        }
    }

}
