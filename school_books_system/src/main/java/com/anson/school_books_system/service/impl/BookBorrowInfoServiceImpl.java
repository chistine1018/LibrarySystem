package com.anson.school_books_system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.anson.school_books_system.po.BookBorrowInfo;
import com.anson.school_books_system.service.BookBorrowInfoService;
import com.anson.school_books_system.mapper.BookBorrowInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author user
* @description 针对表【book_borrow_info】的数据库操作Service实现
* @createDate 2024-07-29 20:07:38
*/
@Service
public class BookBorrowInfoServiceImpl extends ServiceImpl<BookBorrowInfoMapper, BookBorrowInfo>
    implements BookBorrowInfoService{

}




