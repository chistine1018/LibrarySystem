package com.anson.school_books_system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.anson.school_books_system.po.User;
import com.anson.school_books_system.service.UserService;
import com.anson.school_books_system.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author user
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-07-29 20:07:50
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




