package com.anson.school_books_system.po;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 
 * @TableName book_borrow_info
 */
@TableName(value ="book_borrow_info")
@Data
public class BookBorrowInfo implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用戶id
     */
    private Long userId;

    /**
     * 圖書id
     */
    private Long bookId;

    /**
     * 借閱數量
     */
    private Integer borrowCount;

    /**
     * 歸還數量
     */
    private Integer returnCount;

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