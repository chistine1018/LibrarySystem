package com.anson.school_books_system.common.result;


import java.lang.annotation.*;

// 自定義一個註解
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface ResponseResult {
    // 是否忽略
    boolean ignore() default false;
}
