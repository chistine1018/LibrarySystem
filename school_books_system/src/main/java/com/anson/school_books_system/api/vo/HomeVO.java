package com.anson.school_books_system.api.vo;

import lombok.Data;

import java.util.List;

/**
 * @author
 * @package com.anson.school_books_system.api.vo
 * @createTime
 * @description
 */
@Data
public class HomeVO {

    /**
     * 所有輪播圖列表
     */
    private List<String> imgUrlList;

    /**
     * 最新，權重最高的一條公告
     */
    private AnnouncementVO announcementVO;
}
