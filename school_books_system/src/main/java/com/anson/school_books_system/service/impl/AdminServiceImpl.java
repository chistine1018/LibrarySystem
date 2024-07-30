package com.anson.school_books_system.service.impl;

import com.anson.school_books_system.api.form.AuthForm;
import com.anson.school_books_system.api.vo.AdminVO;
import com.anson.school_books_system.converter.UserConverter;
import com.anson.school_books_system.enums.RoleEnum;
import com.anson.school_books_system.utils.PasswordUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.anson.school_books_system.po.Admin;
import com.anson.school_books_system.service.AdminService;
import com.anson.school_books_system.mapper.AdminMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author user
 * @description 針對表【admin】的數據庫操作Service實現
 * @createDate 2024-07-29 19:59:34
 */
@Slf4j
@AllArgsConstructor
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
        implements AdminService {

    private final UserConverter userConverter;

    @Override
    public AdminVO login(AuthForm form) {
        LambdaQueryChainWrapper<Admin> queryChainWrapper = lambdaQuery()
                .eq(Admin::getUsername, form.getUsername())
                .eq(Admin::getPassword, PasswordUtil.encryptPassword(form.getPassword()));
        if (RoleEnum.ROLE_SUPER_ADMIN.equals(form.getRole())) {
            queryChainWrapper.eq(Admin::getRole, 0);
        } else {
            queryChainWrapper.ne(Admin::getRole, 0);
        }
        Admin admin = queryChainWrapper.one();
        if (Objects.isNull(admin)) {
            throw new RuntimeException("帳號不存在或帳號密碼錯誤！");
        }
        return userConverter.converter(admin);
    }

    @Override
    public void saveSuperAdmin(String username, String password) {
        /**
         * 判斷是否存在超級管理員帳號，存在則不添加
         */
        if (lambdaQuery().eq(Admin::getRole, 0).count() > 0) {
            log.info("已經存在超級管理員，不執行保存操作");
            return;
        }
        Admin admin = new Admin();
        admin.setNickName("超級管理員");
        admin.setUsername(username);
        admin.setPassword(PasswordUtil.encryptPassword(password));
        admin.setRole(0);
        save(admin);
        log.info("超級管理員帳號初始化成功！");
    }
}




