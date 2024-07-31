package com.anson.school_books_system.api.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author
 * @package com.anson.school_books_system.api.form
 * @createTime 2024/3/12 - 14:57
 * @description
 */
@Data
public class CarouselMapSaveForm {
    /**
     * 圖片url
     */
    @NotBlank(message = "圖片url不為空")
    private String imgUrl;

    /**
     * 權重（越小越靠前）
     */
    @NotNull(message = "權重不為空")
    private Integer weight;
}
