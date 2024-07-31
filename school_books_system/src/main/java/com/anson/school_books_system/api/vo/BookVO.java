package com.anson.school_books_system.api.vo;

import com.anson.school_books_system.po.Book;
import lombok.Data;

/**
 * @author 
 * @package com.anson.school_books_system.api.vo
 * @createTime
 * @description
 */
@Data
public class BookVO extends Book {
    /**
     * 類別名稱
     */
    private String categoryName;
}
