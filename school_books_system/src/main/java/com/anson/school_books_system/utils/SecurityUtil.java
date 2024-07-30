package com.anson.school_books_system.utils;


import com.anson.school_books_system.api.vo.AdminVO;
import com.anson.school_books_system.api.vo.AuthVO;
import com.anson.school_books_system.api.vo.UserVO;
import com.anson.school_books_system.common.exception.SysException;
import com.anson.school_books_system.enums.RoleEnum;

import java.util.Objects;

public class SecurityUtil {
    private static ThreadLocal<AuthVO> authThreadLocal = new ThreadLocal<>();

    public static ThreadLocal<AuthVO> getAuthThreadLocal() {
        return authThreadLocal;
    }

    public static void setAuthVO(AuthVO authVO) {
        authThreadLocal.set(authVO);
    }

    public static AuthVO getAuth() {
        return getAuthThreadLocal().get();
    }

    public static UserVO getUserVO() {
        if (Objects.isNull(getAuth())) {
            return null;
        }
        return getAuth().getUserVO();
    }

    public static Long getUserId() {
        UserVO userVO = Objects.requireNonNull(getUserVO(), "請登錄在訪問！");
        return userVO.getId();
    }

    public static AdminVO getAdminVO() {
        if (Objects.isNull(getAuth())) {
            return null;
        }
        return getAuth().getAdminVO();
    }

    public static void remove() {
        authThreadLocal.remove();
    }

    public static RoleEnum getRole() {
        AuthVO authVO = getAuth();
        if (Objects.isNull(authVO)) {
            throw new SysException("請登錄在訪問！");
        }
        return authVO.getRole();
    }
}
