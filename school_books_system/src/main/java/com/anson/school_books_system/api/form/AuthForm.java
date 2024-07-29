package com.anson.school_books_system.api.form;

import com.anson.school_books_system.enums.RoleEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author
 * @package com.anson.school_books_system.api.form
 * @createTime
 * @description
 */
@Data
public class AuthForm {
    /**
     * 帳號
     */
    @NotBlank(message = "帳號不為空")
    private String username;
    /**
     * 密碼
     */
    @NotBlank(message = "密碼不為空")
    private String password;
    /**
     * 角色
     */
    @NotNull(message = "角色不為空")
    private RoleEnum role;
}
