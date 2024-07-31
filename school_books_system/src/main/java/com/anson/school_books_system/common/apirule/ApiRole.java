package com.anson.school_books_system.common.apirule;



import com.anson.school_books_system.enums.RoleEnum;

import java.lang.annotation.*;

/**
 * @author
 * @package com.anson.school_books_system.common.apirule
 * @createTime
 * @description
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface ApiRole {
    /**
     * api 運行訪問的角色
     * @return
     */
    RoleEnum[] role();
}
