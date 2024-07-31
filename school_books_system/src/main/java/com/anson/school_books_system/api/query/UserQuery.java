package com.anson.school_books_system.api.query;

import lombok.Data;

/**
 * @author 
 * @package com.anson.school_books_system.api.query
 * @createTime
 * @description
 */
@Data
public class UserQuery extends PageQuery{
    /**
     * 學號
     */
    private String studentNumber;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 學院
     */
    private String college;

    /**
     * 班级
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
}
