package com.anson.school_books_system.service;

import com.anson.school_books_system.api.query.PageQuery;
import com.anson.school_books_system.api.vo.BookCategoryVO;
import com.anson.school_books_system.po.BookCategory;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author user
* @description 針對表【book_category】的數據庫操作Service
* @createDate
*/
public interface BookCategoryService extends IService<BookCategory> {
    IPage<BookCategoryVO> page(PageQuery query, String categoryName);

    void saveOrUpdate(Long id, String categoryName);
}
