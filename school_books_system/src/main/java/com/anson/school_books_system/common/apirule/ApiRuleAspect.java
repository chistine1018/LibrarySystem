package com.anson.school_books_system.common.apirule;



import com.anson.school_books_system.common.exception.SysException;
import com.anson.school_books_system.enums.RoleEnum;
import com.anson.school_books_system.utils.SecurityUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author
 * @package com.anson.school_books_system.common.apirule
 * @createTime
 * @description
 */
@Aspect
@Component
public class ApiRuleAspect {

    @Pointcut("@annotation(com.anson.school_books_system.common.apirule.ApiRole)")
    private void apiRole() {
    }

    /**
     * 前置通知
     */
    @Before("apiRole()")
    public void before(JoinPoint joinPoint) {
        // controller 方法上 没有標註 @ApiRole 注解的，默認不做角色限制
        ApiRole apiRole = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(ApiRole.class);
        if (Objects.isNull(apiRole)) {
            // 不做處理
            return;
        }
        // 獲取注解中指定的角色
        Set<RoleEnum> rule = Arrays.stream(apiRole.role()).collect(Collectors.toSet());
        RoleEnum role = null;
        try {
            role = SecurityUtil.getRole();
        } catch (Exception e) {
            // 這裡報錯，只有一個可能就是没有登錄信息，說明接口不需要認證，所以跳過權限這一步
            return;
        }

        // 對比當前登錄人的角色和接口指定的角色
        if (Boolean.FALSE.equals(rule.contains(role))) {
            throw new SysException("權限不足！");
        }
    }
}
