package com.anson.school_books_system.service;

import com.anson.school_books_system.api.form.AuthForm;
import com.anson.school_books_system.api.form.UserRegisterForm;
import com.anson.school_books_system.api.query.UserQuery;
import com.anson.school_books_system.api.vo.UserVO;
import com.anson.school_books_system.po.User;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author user
 * @description 針對表【user】的數據庫操作Service
 * @createDate 2024-07-29 20:07:50
 */
public interface UserService extends IService<User> {
    UserVO login(AuthForm form);

    void register(UserRegisterForm form);

    IPage<UserVO> page(UserQuery query);

    void update(UserRegisterForm form);

    void delete(Long id);
}
