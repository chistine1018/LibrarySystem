package com.anson.school_books_system.po;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 
 * @TableName admin
 */
@TableName(value ="admin")
@Data
public class Admin implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 角色 :0，表示超級管理員，非 0 表示普通管理員
     */
    @TableField("`role`")
    private Integer role;

    /**
     * 帳號
     */
    private String username;

    /**
     * 密碼
     */
    private String password;

    /**
     * 暱稱
     */
    private String nickName;

    /**
     * 建立時間
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}