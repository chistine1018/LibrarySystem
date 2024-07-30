package com.anson.school_books_system.api.form;


import com.anson.school_books_system.po.Admin;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author <a href=""></a>
 * @package com.anson.school_books_system.api.form
 * @createTime
 * @description
 */
//@Accessors(chain = true)
@Data
public class AdminSaveOrUpdateForm extends Admin {

    /**
     * 保存id不填，修改id必填
     */
    private Long id;

    /**
     * 帳號
     */
    @NotBlank(message = "帳號不為空")
    private String username;

    /**
     * 密码
     */
//    @NotBlank(message = "密碼不為空")
    private String password;

    /**
     * 昵称
     */
    @NotBlank(message = "暱稱不为空")
    private String nickName;
}
