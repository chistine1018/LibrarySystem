package com.anson.school_books_system.service;

import com.anson.school_books_system.api.form.CarouselMapSaveForm;
import com.anson.school_books_system.api.query.PageQuery;
import com.anson.school_books_system.api.vo.CarouselMapVO;
import com.anson.school_books_system.po.CarouselMap;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author user
* @description 針對表【carousel_map】的數據庫操作Service
* @createDate 2024-07-29 20:07:43
*/
public interface CarouselMapService extends IService<CarouselMap> {
    IPage<CarouselMapVO> page(PageQuery query);

    void save(CarouselMapSaveForm form);
}
