package com.anson.school_books_system.po;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 
 * @TableName carousel_map
 */
@TableName(value ="carousel_map")
@Data
public class CarouselMap implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 圖片url
     */
    private String imgUrl;

    /**
     * 權重（越小越前）
     */
    private Integer weight;

    /**
     * 建立時間
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