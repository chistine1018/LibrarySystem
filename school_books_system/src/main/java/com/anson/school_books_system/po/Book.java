package com.anson.school_books_system.po;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 
 * @TableName book
 */
@TableName(value ="book")
@Data
public class Book implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

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

    /**
     * 作者
     */
    private String author;

    /**
     * 類別
     */
    private Long categoryId;

    /**
     * 單價
     */
    private BigDecimal price;

    /**
     * 出版社
     */
    private String publishingHouse;

    /**
     * 出版日期
     */
    private LocalDate publicationDate;

    /**
     * 總數
     */
    private Integer total;

    /**
     * 目前數量
     */
    private Integer extantTotal;

    /**
     * 內容摘要
     */
    private String intro;

    /**
     * 入庫時間，建立時間
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 建立人
     */
    @TableField(fill = FieldFill.INSERT)
    private String creator;

    /**
     * 修改時間
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 修改人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String modifier;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}