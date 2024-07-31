package com.anson.school_books_system.mapper;

import com.anson.school_books_system.api.query.BookQuery;
import com.anson.school_books_system.api.vo.BookVO;
import com.anson.school_books_system.po.Book;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author user
 * @description 針對表【book】的數據庫操作Mapper
 * @createDate 2024-07-29 20:07:35
 * @Entity com.anson.school_books_system.po.Book
 */
public interface BookMapper extends BaseMapper<Book> {

    List<Book> bookListBy(@Param("bookNumber") String bookNumber, @Param("bookName") String bookName);

    BookVO one(@Param("id") Long id);

    IPage<BookVO> page(@Param("page") Page<BookVO> page, @Param("query") BookQuery query);
}





