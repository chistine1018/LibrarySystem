package com.anson.school_books_system.api.vo;

import com.anson.school_books_system.enums.RoleEnum;
import lombok.Data;

@Data
public class AuthVO {

    private RoleEnum role;

    private String token;

    private AdminVO adminVO;

    private UserVO userVO;
}
