package com.anson.school_books_system.converter;


import com.anson.school_books_system.api.vo.BookCategoryVO;
import com.anson.school_books_system.api.vo.BookVO;
import com.anson.school_books_system.po.Book;
import com.anson.school_books_system.po.BookCategory;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author 
 * @package com.anson.school_books_system.converter
 * @createTime 
 * @description
 */
@Mapper(componentModel = "spring", typeConversionPolicy = ReportingPolicy.ERROR)
public interface BookConverter {
    BookCategoryVO converter(BookCategory bookCategory);

    BookVO converter(Book book);

}
