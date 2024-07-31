package com.anson.school_books_system.mapper;

import com.anson.school_books_system.api.query.UserReturnBookQuery;
import com.anson.school_books_system.api.vo.UserReturnBookInfoVO;
import com.anson.school_books_system.po.UserReturnBookInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
* @author user
* @description 针对表【user_return_book_info】的数据库操作Mapper
* @createDate 2024-07-29 20:07:52
* @Entity com.anson.school_books_system.po.UserReturnBookInfo
*/
public interface UserReturnBookInfoMapper extends BaseMapper<UserReturnBookInfo> {

    IPage<UserReturnBookInfoVO> page(@Param("page") Page<UserReturnBookInfoVO> page, @Param("query") UserReturnBookQuery query);
}




