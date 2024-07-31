package com.anson.school_books_system.service;

import com.anson.school_books_system.api.form.AnnouncementSaveForm;
import com.anson.school_books_system.api.query.AnnouncementQuery;
import com.anson.school_books_system.api.vo.AnnouncementVO;
import com.anson.school_books_system.po.Announcement;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author user
* @description 針對表【announcement】的數據庫操作Service
* @createDate 2024-07-29 20:07:18
*/
public interface AnnouncementService extends IService<Announcement> {
    IPage<AnnouncementVO> page(AnnouncementQuery query);

    void save(AnnouncementSaveForm form);
}
