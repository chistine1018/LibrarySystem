package com.anson.school_books_system.service.impl;

import com.anson.school_books_system.api.form.AnnouncementSaveForm;
import com.anson.school_books_system.api.query.AnnouncementQuery;
import com.anson.school_books_system.api.vo.AnnouncementVO;
import com.anson.school_books_system.converter.AnnouncementConverter;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.anson.school_books_system.po.Announcement;
import com.anson.school_books_system.service.AnnouncementService;
import com.anson.school_books_system.mapper.AnnouncementMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
* @author user
* @description 針對表【announcement】的數據庫操作Service實現
* @createDate 2024-07-29 20:07:18
*/
@Slf4j
@AllArgsConstructor
@Service
public class AnnouncementServiceImpl extends ServiceImpl<AnnouncementMapper, Announcement>
    implements AnnouncementService{

    private final AnnouncementConverter announcementConverter;

    @Override
    public IPage<AnnouncementVO> page(AnnouncementQuery query) {
        Page<Announcement> announcementPage = lambdaQuery()
                .like(StringUtils.isNotBlank(query.getTitle()), Announcement::getTitle, query.getTitle())
                .gt(Objects.nonNull(query.getStartTime()), Announcement::getCreateTime, query.getStartTime())
                .lt(Objects.nonNull(query.getEndTime()), Announcement::getCreateTime, Objects.isNull(query.getEndTime()) ? null : query.getEndTime().plusDays(1))
                .page(query.getPage());
        return announcementPage.convert(announcementConverter::converter);
    }

    @Override
    public void save(AnnouncementSaveForm form) {
        save(announcementConverter.converter(form));
    }
}




