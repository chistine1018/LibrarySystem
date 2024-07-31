package com.anson.school_books_system.converter;


import com.anson.school_books_system.api.form.AdminSaveOrUpdateForm;
import com.anson.school_books_system.api.form.UserRegisterForm;
import com.anson.school_books_system.api.vo.AdminVO;
import com.anson.school_books_system.api.vo.UserVO;
import com.anson.school_books_system.po.Admin;
import com.anson.school_books_system.po.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", typeConversionPolicy = ReportingPolicy.ERROR)
public interface UserConverter {
    AdminVO converter(Admin admin);

    Admin converter(AdminSaveOrUpdateForm form);

    UserVO converter(User user);

    User converter(UserRegisterForm form);
}
