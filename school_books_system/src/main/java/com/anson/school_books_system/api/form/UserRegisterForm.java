package com.anson.school_books_system.api.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author <a href=""></a>
 * @package com.anson.school_books_system.api.form
 * @createTime
 * @description
 */
@Data
public class UserRegisterForm {

    /**
     * 註冊用戶不填id，修改用戶傳id
     */
    private Long id;

    /**
     * 學號
     */
    @NotBlank(message = "學號不為空")
    private String studentNumber;

    /**
     * 密碼
     */
//    @NotBlank(message = "密碼不為空")
    private String password;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不為空")
    private String name;

    /**
     * 性别
     */
    @NotNull(message = "性别不為空")
    private Integer sex;

    /**
     * 學院
     */
    @NotBlank(message = "學院不為空")
    private String college;

    /**
     * 班級
     */
    @NotBlank(message = "班級不為空")
    private String grade;

    /**
     * 電話
     */
    @NotBlank(message = "電話不為空")
    private String phone;

    /**
     * 宿舍
     */
    @NotBlank(message = "宿舍不為空")
    private String dormitory;
}
