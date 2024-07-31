package com.anson.school_books_system.service.impl;

import com.anson.school_books_system.api.form.BookSaveOrUpdateForm;
import com.anson.school_books_system.api.query.BookQuery;
import com.anson.school_books_system.api.vo.BookVO;
import com.anson.school_books_system.common.exception.SysException;
import com.anson.school_books_system.converter.BookConverter;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.anson.school_books_system.po.Book;
import com.anson.school_books_system.service.BookService;
import com.anson.school_books_system.mapper.BookMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author user
 * @description 針對表【book】的數據庫操作Service實現
 * @createDate 2024-07-29 20:07:35
 */
@Slf4j
@AllArgsConstructor
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book>
        implements BookService {

    private final BookConverter bookConverter;


    @Override
    public IPage<BookVO> page(BookQuery query) {
        return getBaseMapper().page(query.getPage(), query);
    }

    @Override
    public void saveOrUpdate(BookSaveOrUpdateForm form) {
        // case 1 沒帶ID 檢查是否有存在，沒存在再新增
        // case 2 有帶ID 進行更改
        List<Book> bookList = getBaseMapper().bookListBy(form.getBookNumber(), form.getBookName());
        long count = bookList.stream().filter(item -> Boolean.FALSE.equals(item.getId().equals(form.getId())))
                .count();
        if (count > 0) {
            throw new SysException("圖書編號或名稱已存在！");
        }
        Book book = bookConverter.converter(form);
        if (Objects.isNull(book.getId())) {
            book.setExtantTotal(book.getTotal());
            save(book);
        } else {
            Book loadBook = getById(form.getId());
            // 計算可用庫存 = 新庫存 - 借出去的庫存
            book.setExtantTotal(form.getTotal() - (loadBook.getTotal() - loadBook.getExtantTotal()));
            if (book.getExtantTotal() < 0) {
                throw new SysException("已借出去的圖書大於此庫存數，請正確填寫！");
            }
            updateById(book);
        }
    }

    @Override
    public BookVO one(Long id) {
        BookVO vo = getBaseMapper().one(id);
        if (Objects.isNull(vo)) {
            throw new SysException("圖書信息不存在！");
        }
        return vo;
    }
}




