package com.anson.school_books_system.service.impl;

import com.anson.school_books_system.api.query.PageQuery;
import com.anson.school_books_system.api.vo.BookCategoryVO;
import com.anson.school_books_system.converter.BookConverter;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.anson.school_books_system.po.BookCategory;
import com.anson.school_books_system.service.BookCategoryService;
import com.anson.school_books_system.mapper.BookCategoryMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
* @author user
* @description 針對表【book_category】的數據庫操作Service實現
* @createDate 2024-07-29 20:07:41
*/
@Slf4j
@AllArgsConstructor
@Service
public class BookCategoryServiceImpl extends ServiceImpl<BookCategoryMapper, BookCategory>
    implements BookCategoryService{

    private final BookConverter bookConverter;

    @Override
    public IPage<BookCategoryVO> page(PageQuery query, String categoryName) {
        Page<BookCategory> categoryPage = lambdaQuery()
                .like(StringUtils.isNotBlank(categoryName), BookCategory::getCategoryName, categoryName)
                .page(query.getPage());
        return categoryPage.convert(bookConverter::converter);
    }

    @Override
    public void saveOrUpdate(Long id, String categoryName) {
        if (lambdaQuery().eq(BookCategory::getCategoryName, categoryName)
                .ne(Objects.nonNull(id), BookCategory::getId, id).count() > 0) {
            throw new RuntimeException("該分類已存在");
        }

        BookCategory bookCategory = new BookCategory();
        bookCategory.setId(id);
        bookCategory.setCategoryName(categoryName);
        if (Objects.isNull(bookCategory.getId())) {
            save(bookCategory);
        }
        updateById(bookCategory);
    }
}




