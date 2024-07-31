package com.anson.school_books_system.service.impl;

import com.anson.school_books_system.api.query.BookBorrowInfoQuery;
import com.anson.school_books_system.api.vo.BookBorrowInfoVO;
import com.anson.school_books_system.common.exception.SysException;
import com.anson.school_books_system.po.Book;
import com.anson.school_books_system.service.BookService;
import com.anson.school_books_system.utils.SecurityUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.anson.school_books_system.po.BookBorrowInfo;
import com.anson.school_books_system.service.BookBorrowInfoService;
import com.anson.school_books_system.mapper.BookBorrowInfoMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
* @author user
* @description 針對表【book_borrow_info】的數據庫操作Service實現
* @createDate 2024-07-29 20:07:38
*/
@Slf4j
@AllArgsConstructor
@Service
public class BookBorrowInfoServiceImpl extends ServiceImpl<BookBorrowInfoMapper, BookBorrowInfo>
    implements BookBorrowInfoService{

    private final BookService bookService;

    @Override
    public IPage<BookBorrowInfoVO> page(BookBorrowInfoQuery query) {

        return getBaseMapper().page(query.getPage(), query);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void bookBorrow(Long bookId, Integer borrowTotal) {
        // 校驗圖書庫存
        Book book = Objects.requireNonNull(bookService.getById(bookId), "圖書信息不存在");
        if (book.getExtantTotal() < borrowTotal) {
            throw new SysException("圖書庫存不足");
        }

        /*
        如果借閱存在，合併
        如果不存在，新增
         */
        BookBorrowInfo borrowInfo = lambdaQuery()
                .eq(BookBorrowInfo::getBookId, bookId)
                .eq(BookBorrowInfo::getUserId, SecurityUtil.getUserId())
                .one();

        if (Objects.isNull(borrowInfo)) {
            borrowInfo = new BookBorrowInfo();
            borrowInfo.setUserId(SecurityUtil.getUserId());
            borrowInfo.setBookId(bookId);
            borrowInfo.setBorrowCount(borrowTotal);
            save(borrowInfo);
        } else {
            borrowInfo.setBorrowCount(borrowTotal + borrowInfo.getBorrowCount());
            updateById(borrowInfo);
        }

        book.setExtantTotal(book.getExtantTotal() - borrowTotal);
        bookService.updateById(book);
    }
}




