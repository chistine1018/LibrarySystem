package com.anson.school_books_system.mapper;

import com.anson.school_books_system.api.query.BookBorrowInfoQuery;
import com.anson.school_books_system.api.vo.BookBorrowInfoVO;
import com.anson.school_books_system.po.BookBorrowInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
* @author user
* @description 針對表【book_borrow_info】的數據庫操作Mapper
* @createDate 2024-07-29 20:07:38
* @Entity com.anson.school_books_system.po.BookBorrowInfo
*/
public interface BookBorrowInfoMapper extends BaseMapper<BookBorrowInfo> {
    IPage<BookBorrowInfoVO> page(@Param("page") Page<BookBorrowInfoVO> page, @Param("query") BookBorrowInfoQuery query);

}




