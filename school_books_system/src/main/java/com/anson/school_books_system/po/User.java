package com.anson.school_books_system.po;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 學號
     */
    private String studentNumber;

    /**
     * 密碼
     */
    private String password;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性別
     */
    private Integer sex;

    /**
     * 學院
     */
    private String college;

    /**
     * 班級
     */
    private String grade;

    /**
     * 電話
     */
    private String phone;

    /**
     * 宿舍
     */
    private String dormitory;

    /**
     * 建立時間
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改時間
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}