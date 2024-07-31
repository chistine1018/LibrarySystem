package com.anson.school_books_system.service.impl;


import com.anson.school_books_system.api.vo.AnnouncementVO;
import com.anson.school_books_system.api.vo.HomeVO;
import com.anson.school_books_system.converter.AnnouncementConverter;
import com.anson.school_books_system.po.Announcement;
import com.anson.school_books_system.po.CarouselMap;
import com.anson.school_books_system.service.AnnouncementService;
import com.anson.school_books_system.service.CarouselMapService;
import com.anson.school_books_system.service.HomeService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * @author
 * @package com.anson.school_books_system.service.impl
 * @createTime
 * @description
 */
@AllArgsConstructor
@Slf4j
@Service
public class HomeServiceImpl implements HomeService {

    private final AnnouncementService announcementService;

    private final CarouselMapService carouselMapService;

    private final AnnouncementConverter announcementConverter;

    @Override
    public HomeVO home() {
        HomeVO homeVO = new HomeVO();
        homeVO.setAnnouncementVO(new AnnouncementVO());
        homeVO.setImgUrlList(
                carouselMapService.lambdaQuery().orderByAsc(CarouselMap::getWeight)
                        .list().stream().map(CarouselMap::getImgUrl).collect(Collectors.toList())
        );
        Page<Announcement> page = announcementService.lambdaQuery()
                .orderByAsc(Announcement::getWeight)
                .orderByDesc(Announcement::getCreateTime)
                .page(new Page<>(1, 1));
        if (page.getTotal() > 0) {
            homeVO.setAnnouncementVO(page.convert(announcementConverter::converter).getRecords().get(0));
        }
        return homeVO;
    }
}
