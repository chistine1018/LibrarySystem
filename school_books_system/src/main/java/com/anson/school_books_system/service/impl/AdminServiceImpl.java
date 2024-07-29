package com.anson.school_books_system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.anson.school_books_system.po.Admin;
import com.anson.school_books_system.service.AdminService;
import com.anson.school_books_system.mapper.AdminMapper;
import org.springframework.stereotype.Service;

/**
* @author user
* @description 针对表【admin】的数据库操作Service实现
* @createDate 2024-07-29 19:59:34
*/
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
    implements AdminService{

}




