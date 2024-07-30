package com.anson.school_books_system.interceptor;


import com.anson.school_books_system.api.vo.AuthVO;
import com.anson.school_books_system.common.exception.SysException;
import com.anson.school_books_system.service.SysTokenService;
import com.anson.school_books_system.utils.SecurityUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Slf4j
@AllArgsConstructor
@Component
public class SecurityInterceptor implements HandlerInterceptor {

    private final SysTokenService sysTokenService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equals("OPTIONS")) {
            /**
             * 放開 OPTIONS 請求，指攔截固定的四種請求（不放開會有跨域錯誤）
             */
            return true;
        }
        if (request.getRequestURI().contains("/static/img")){
            return HandlerInterceptor.super.preHandle(request, response, handler);
        }
        String token = request.getHeader("Authorization");
        if (StringUtils.isBlank(token)) {
            throw new SysException("請登錄在訪問！");
        }
        AuthVO authVO = sysTokenService.getByToken(token);
        if (Objects.isNull(authVO)) {
            throw new SysException("認證不通過，請登錄在訪問！");
        }
        SecurityUtil.setAuthVO(authVO);



        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        SecurityUtil.remove();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
