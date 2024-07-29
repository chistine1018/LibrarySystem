package com.anson.school_books_system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.anson.school_books_system.po.Book;
import com.anson.school_books_system.service.BookService;
import com.anson.school_books_system.mapper.BookMapper;
import org.springframework.stereotype.Service;

/**
* @author user
* @description 针对表【book】的数据库操作Service实现
* @createDate 2024-07-29 20:07:35
*/
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book>
    implements BookService{

}




