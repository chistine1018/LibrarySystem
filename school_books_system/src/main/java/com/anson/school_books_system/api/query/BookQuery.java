package com.anson.school_books_system.api.query;

import lombok.Data;

/**
 * @author
 * @package com.anson.school_books_system.api.query
 * @createTime
 * @description
 */
@Data
public class BookQuery extends PageQuery{
    /**
     * 圖書編號
     */
    private String bookNumber;

    /**
     * 書名
     */
    private String bookName;

    /**
     * 作者
     */
    private String author;

    /**
     * 類別
     */
    private Long categoryId;

    /**
     * 出版社
     */
    private String publishingHouse;
}
