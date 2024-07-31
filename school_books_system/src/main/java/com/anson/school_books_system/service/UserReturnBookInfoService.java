package com.anson.school_books_system.service;

import com.anson.school_books_system.api.query.UserReturnBookQuery;
import com.anson.school_books_system.api.vo.UserReturnBookInfoVO;
import com.anson.school_books_system.po.UserReturnBookInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author user
* @description 針對表【user_return_book_info】的數據庫操作Service
* @createDate 2024-07-29 20:07:52
*/
public interface UserReturnBookInfoService extends IService<UserReturnBookInfo> {
    IPage<UserReturnBookInfoVO> page(UserReturnBookQuery query);

    void userReturnBook(Long bookBorrowInfoId, Integer count);
}
