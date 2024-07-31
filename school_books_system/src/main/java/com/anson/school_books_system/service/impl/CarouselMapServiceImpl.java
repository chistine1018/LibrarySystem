package com.anson.school_books_system.service.impl;

import com.anson.school_books_system.api.form.CarouselMapSaveForm;
import com.anson.school_books_system.api.query.PageQuery;
import com.anson.school_books_system.api.vo.CarouselMapVO;
import com.anson.school_books_system.converter.AnnouncementConverter;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.anson.school_books_system.po.CarouselMap;
import com.anson.school_books_system.service.CarouselMapService;
import com.anson.school_books_system.mapper.CarouselMapMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
* @author user
* @description 針對表【carousel_map】的數據庫操作Service實現
* @createDate 2024-07-29 20:07:43
*/
@AllArgsConstructor
@Slf4j
@Service
public class CarouselMapServiceImpl extends ServiceImpl<CarouselMapMapper, CarouselMap>
    implements CarouselMapService{
    private final AnnouncementConverter announcementConverter;

    @Override
    public IPage<CarouselMapVO> page(PageQuery query) {
        Page<CarouselMap> carouselMapPage = lambdaQuery()
                .orderByAsc(CarouselMap::getWeight)
                .page(query.getPage());
        return carouselMapPage.convert(announcementConverter::converter);
    }

    @Override
    public void save(CarouselMapSaveForm form) {
        CarouselMap carouselMap = new CarouselMap();
        carouselMap.setImgUrl(form.getImgUrl());
        carouselMap.setWeight(form.getWeight());
        save(carouselMap);
    }
}




