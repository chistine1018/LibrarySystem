package com.anson.school_books_system.api.query;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author
 * @package com.anson.school_books_system.api.query
 * @createTime
 * @description
 */
@Data
public class UserReturnBookQuery extends PageQuery{

    /**
     * 用戶id
     */
    private Long userId;

    /**
     * 學號
     */
    private String studentNumber;

    /**
     * 姓名
     */
    private String name;

    /**
     * 圖書編號
     */
    private String bookNumber;

    /**
     * 書名
     */
    private String bookName;

    /**
     * 歸還時間
     */
    private LocalDate startTime;
    private LocalDate endTime;
}
