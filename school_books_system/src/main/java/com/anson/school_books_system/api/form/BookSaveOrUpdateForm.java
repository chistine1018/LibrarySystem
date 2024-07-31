package com.anson.school_books_system.api.form;

import com.anson.school_books_system.po.Book;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author
 * @package com.anson.school_books_system.api.form
 * @createTime
 * @description
 */
@Data
public class BookSaveOrUpdateForm extends Book {
    /**
     * 保存不傳id，修改必傳
     */
    private Long id;

    /**
     * 圖書編號
     */
    @NotBlank(message = "圖書編號不為空")
    private String bookNumber;

    /**
     * 封面url
     */
    @NotBlank(message = "封面url不為空")
    private String coverUrl;

    /**
     * 書名
     */
    @NotBlank(message = "書名不為空")
    private String bookName;

    /**
     * 作者
     */
    @NotBlank(message = "作者不為空")
    private String author;

    /**
     * 類別
     */
    @NotNull(message = "類別不為空")
    private Long categoryId;

    /**
     * 單價
     */
    @NotNull(message = "單價不為空")
    private BigDecimal price;

    /**
     * 出版社
     */
    @NotBlank(message = "出版社不為空")
    private String publishingHouse;

    /**
     * 出版日期
     */
    @NotNull(message = "出版日期不為空")
    private LocalDate publicationDate;

    /**
     * 總數量
     */
    @NotNull(message = "總數量不為空")
    private Integer total;

    /**
     * 内容摘要
     */
    @NotBlank(message = "内容摘要不為空")
    private String intro;
}
