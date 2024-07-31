package com.anson.school_books_system.converter;

import com.anson.school_books_system.api.vo.UserReturnBookInfoVO;
import com.anson.school_books_system.po.UserReturnBookInfo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author
 * @package com.anson.school_books_system.converter
 * @createTime
 * @description
 */
@Mapper(componentModel = "spring", typeConversionPolicy = ReportingPolicy.ERROR)
public interface BookBorrowInfoConverter {

    UserReturnBookInfoVO converter(UserReturnBookInfo userReturnBookInfo);
}
