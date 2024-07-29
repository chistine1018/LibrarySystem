package com.anson.school_books_system.service.impl;

import com.anson.school_books_system.api.form.AuthForm;
import com.anson.school_books_system.api.vo.UserVO;
import com.anson.school_books_system.common.exception.AuthException;
import com.anson.school_books_system.converter.UserConverter;
import com.anson.school_books_system.utils.PasswordUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.anson.school_books_system.po.User;
import com.anson.school_books_system.service.UserService;
import com.anson.school_books_system.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
* @author user
* @description 針對表【user】的數據庫操作Service實現
* @createDate 2024-07-29 20:07:50
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    UserConverter userConverter;

    @Override
    public UserVO login(AuthForm form) {
        User user = lambdaQuery()
                .eq(User::getStudentNumber, form.getUsername())
//                .eq(User::getPassword, form.getPassword())
                .eq(User::getPassword, PasswordUtil.encryptPassword(form.getPassword()))
                .one();

        if (Objects.isNull(user)) {
            throw new AuthException("帳號不存在或者登陸訊息錯誤");
        }


        return userConverter.converter(user);
    }
}




