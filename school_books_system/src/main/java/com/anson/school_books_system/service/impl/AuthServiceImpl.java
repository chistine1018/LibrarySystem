package com.anson.school_books_system.service.impl;


import com.anson.school_books_system.api.form.AuthForm;
import com.anson.school_books_system.api.vo.AuthVO;
import com.anson.school_books_system.enums.RoleEnum;
import com.anson.school_books_system.service.AdminService;
import com.anson.school_books_system.service.AuthService;
import com.anson.school_books_system.service.SysTokenService;
import com.anson.school_books_system.service.UserService;
import com.anson.school_books_system.utils.SecurityUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private AdminService adminService;

    private UserService userService;

    private SysTokenService sysTokenService;


    @Override
    public AuthVO auth(AuthForm form) {
        AuthVO authVO = new AuthVO();
        authVO.setRole(form.getRole());

        if (RoleEnum.ROLE_USER.equals(form.getRole())) {
            authVO.setUserVO(userService.login(form));
        } else {
            authVO.setAdminVO(adminService.login(form));
        }

        authVO.setToken(sysTokenService.createToken(authVO));
        return authVO;
    }

    @Override
    public void logout() {
        sysTokenService.removeByToken(SecurityUtil.getAuth().getToken());
    }

}
