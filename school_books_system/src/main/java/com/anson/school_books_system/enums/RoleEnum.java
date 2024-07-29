package com.anson.school_books_system.enums;

import lombok.Getter;

@Getter
public enum RoleEnum {
    ROLE_SUPER_ADMIN(0, "超级管理员"),
    ROLE_ADMIN(1, "普通管理员"),
    ROLE_USER(2, "用户");

    private Integer code;
    private String desc;

    RoleEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}