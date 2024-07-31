package com.anson.school_books_system.api.vo;

import com.anson.school_books_system.po.BookBorrowInfo;
import lombok.Data;

/**
 * @author
 * @package com.anson.school_books_system.api.vo
 * @createTime
 * @description
 */
@Data
public class BookBorrowInfoVO extends BookBorrowInfo {

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
     * 封面url
     */
    private String coverUrl;

    /**
     * 書名
     */
    private String bookName;
}
