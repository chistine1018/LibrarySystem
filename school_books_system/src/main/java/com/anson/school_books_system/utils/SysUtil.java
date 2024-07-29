package com.anson.school_books_system.utils;

import cn.hutool.core.util.RandomUtil;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 
 * @package com.anson.school_books_system.utils
 * @createTime
 * @description
 */
public class SysUtil {

    /**
     * 手機號碼正則
     */
    public final static String PHONE_REGEX = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$";


    /**
     * 生成 token 字符串的方法
     * @return
     */
    public static String getToken() {
        return RandomUtil.randomString(32);
    }

    /**
     * 生成 token 過期時間
     * @return
     */
    public static LocalDateTime getTokenExpiredTime() {
        return LocalDateTime.now().plusDays(5);
    }

    /**
     * 驗證手機號格式
     *
     * @param phone
     * @return
     */
    public static Boolean checkPhone(String phone) {
        Pattern pattern = Pattern.compile(PHONE_REGEX);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }
}
