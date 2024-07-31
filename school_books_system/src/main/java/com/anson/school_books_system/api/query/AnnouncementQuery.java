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
public class AnnouncementQuery extends PageQuery {
    /**
     * 公告標題
     */
    private String title;
    /**
     * 公告發布時間
     */
    private LocalDate startTime;
    private LocalDate endTime;
}
