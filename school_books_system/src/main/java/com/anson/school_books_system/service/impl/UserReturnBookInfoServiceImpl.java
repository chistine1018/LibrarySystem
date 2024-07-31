package com.anson.school_books_system.service.impl;

import com.anson.school_books_system.api.query.UserReturnBookQuery;
import com.anson.school_books_system.api.vo.UserReturnBookInfoVO;
import com.anson.school_books_system.common.exception.SysException;
import com.anson.school_books_system.converter.BookBorrowInfoConverter;
import com.anson.school_books_system.po.Book;
import com.anson.school_books_system.po.BookBorrowInfo;
import com.anson.school_books_system.service.BookBorrowInfoService;
import com.anson.school_books_system.service.BookService;
import com.anson.school_books_system.utils.SecurityUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.anson.school_books_system.po.UserReturnBookInfo;
import com.anson.school_books_system.service.UserReturnBookInfoService;
import com.anson.school_books_system.mapper.UserReturnBookInfoMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
* @author user
* @description 針對表【user_return_book_info】的數據庫操作Service實現
* @createDate 2024-07-29 20:07:52
*/
@Slf4j
@AllArgsConstructor
@Service
public class UserReturnBookInfoServiceImpl extends ServiceImpl<UserReturnBookInfoMapper, UserReturnBookInfo>
    implements UserReturnBookInfoService{

    private final BookBorrowInfoConverter bookBorrowInfoConverter;
    private final BookBorrowInfoService bookBorrowInfoService;
    private final BookService bookService;

    @Override
    public IPage<UserReturnBookInfoVO> page(UserReturnBookQuery query) {
        return getBaseMapper().page(query.getPage(), query);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void userReturnBook(Long bookBorrowInfoId, Integer count) {
        BookBorrowInfo bookBorrowInfo = Objects.requireNonNull(
                bookBorrowInfoService
                        .lambdaQuery().eq(BookBorrowInfo::getId, bookBorrowInfoId)
                        .eq(BookBorrowInfo::getUserId, SecurityUtil.getUserId())
                        .one(), "借閱信息不存在！");
        if (bookBorrowInfo.getBorrowCount().equals(bookBorrowInfo.getReturnCount())) {
            throw new SysException("已全部歸還，無須再歸還圖書");
        }
        Integer returnCount = bookBorrowInfo.getReturnCount();
        bookBorrowInfo.setReturnCount(returnCount + count);
        if (bookBorrowInfo.getReturnCount() > bookBorrowInfo.getBorrowCount()) {
            throw new SysException("圖書歸還數目不對，你只需歸還：" + (bookBorrowInfo.getBorrowCount() - returnCount));
        }
        Book book = Objects.requireNonNull(bookService.getById(bookBorrowInfo.getBookId()), "圖書不存在！");

        // 修改借閱信息的歸還數量
        bookBorrowInfoService.updateById(bookBorrowInfo);
        // 修改圖書的剩餘數量
        book.setExtantTotal(book.getExtantTotal() + count);
        bookService.updateById(book);

        // 保存用戶的歸還信息
        UserReturnBookInfo userReturnBookInfo = new UserReturnBookInfo();
        userReturnBookInfo.setUserId(SecurityUtil.getUserId());
        userReturnBookInfo.setBookId(bookBorrowInfo.getBookId());
        userReturnBookInfo.setReturnCount(count);
        save(userReturnBookInfo);
    }
}




