package com.anson.school_books_system.api.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author
 * @package com.anson.school_books_system.api.form
 * @createTime
 * @description
 */
@Data
public class AnnouncementSaveForm {
    /**
     * 標題
     */
    @NotBlank(message = "標題不為空")
    private String title;

    /**
     * 内容
     */
    @NotBlank(message = "内容不為空")
    private String content;

    /**
     * 權重（越小越靠前）
     */
    @NotNull(message = "權重不為空")
    private Integer weight;
}
