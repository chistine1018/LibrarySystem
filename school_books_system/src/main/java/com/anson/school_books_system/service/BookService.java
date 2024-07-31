package com.anson.school_books_system.service;

import com.anson.school_books_system.api.form.BookSaveOrUpdateForm;
import com.anson.school_books_system.api.query.BookQuery;
import com.anson.school_books_system.api.vo.BookVO;
import com.anson.school_books_system.po.Book;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author user
* @description 針對表【book】的數據庫操作Service
* @createDate 2024-07-29 20:07:35
*/
public interface BookService extends IService<Book> {
    IPage<BookVO> page(BookQuery query);

    void saveOrUpdate(BookSaveOrUpdateForm form);

    BookVO one(Long id);
}
