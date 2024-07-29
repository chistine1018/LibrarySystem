package com.anson.school_books_system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.anson.school_books_system.po.BookCategory;
import com.anson.school_books_system.service.BookCategoryService;
import com.anson.school_books_system.mapper.BookCategoryMapper;
import org.springframework.stereotype.Service;

/**
* @author user
* @description 针对表【book_category】的数据库操作Service实现
* @createDate 2024-07-29 20:07:41
*/
@Service
public class BookCategoryServiceImpl extends ServiceImpl<BookCategoryMapper, BookCategory>
    implements BookCategoryService{

}




