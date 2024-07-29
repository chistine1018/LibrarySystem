package com.anson.school_books_system.service;

import com.anson.school_books_system.api.form.AuthForm;
import com.anson.school_books_system.api.vo.AdminVO;
import com.anson.school_books_system.po.Admin;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author user
* @description 針對表【admin】的數據庫操作Service
* @createDate 2024-07-29 19:59:34
*/
public interface AdminService extends IService<Admin> {
    AdminVO login(AuthForm form);
}
