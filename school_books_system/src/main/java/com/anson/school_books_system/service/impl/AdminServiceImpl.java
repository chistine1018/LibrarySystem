package com.anson.school_books_system.service.impl;

import com.anson.school_books_system.api.form.AdminSaveOrUpdateForm;
import com.anson.school_books_system.api.form.AuthForm;
import com.anson.school_books_system.api.query.AdminQuery;
import com.anson.school_books_system.api.vo.AdminVO;
import com.anson.school_books_system.common.exception.SysException;
import com.anson.school_books_system.converter.UserConverter;
import com.anson.school_books_system.enums.RoleEnum;
import com.anson.school_books_system.utils.PasswordUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.anson.school_books_system.po.Admin;
import com.anson.school_books_system.service.AdminService;
import com.anson.school_books_system.mapper.AdminMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    @Override
    public AdminVO saveOrUpdate(AdminSaveOrUpdateForm form) {
        Admin admin = userConverter.converter(form);

        // 先根據帳號查詢是否存在該帳號
        Admin existingAdmin = lambdaQuery().eq(Admin::getUsername, admin.getUsername()).one();

        if (existingAdmin != null) {
            // 如果帳號存在，且admin.getId()為null，說明是新創建的帳號，返回錯誤信息
            if (admin.getId() == null) {
                throw new SysException("管理員帳號已存在！");
            }
            // 如果帳號存在，且admin.getId()不為null，說明是修改帳號，設置ID以便更新
            admin.setId(existingAdmin.getId());
            admin.setRole(existingAdmin.getRole()); // 確保角色不會被覆蓋
            // 密碼加密
            if (!existingAdmin.getPassword().equals(admin.getPassword())) {
                // 密碼加密
                admin.setPassword(PasswordUtil.encryptPassword(admin.getPassword()));
            } else {
                // 如果密碼未改變，使用原來的密碼
                admin.setPassword(existingAdmin.getPassword());
            }
            updateById(admin);
        } else {
            // 如果帳號不存在，進行創建操作
            admin.setRole(1); // 默認角色設置為1
            // 密碼加密
            admin.setPassword(PasswordUtil.encryptPassword(admin.getPassword()));
            save(admin);
        }

        return userConverter.converter(getById(admin.getId()));
    }


    @Override
    public IPage<AdminVO> page(AdminQuery query) {
        Page<Admin> adminPage = lambdaQuery()
                .like(StringUtils.isNotBlank(query.getNickName()), Admin::getNickName, query.getNickName())
                .like(StringUtils.isNotBlank(query.getUsername()), Admin::getUsername, query.getUsername())
                .page(query.getPage());
        return adminPage.convert(userConverter::converter);
    }
}




