package com.anson.school_books_system.utils;

import cn.hutool.crypto.digest.MD5;
import com.anson.school_books_system.common.exception.SysException;
import org.apache.commons.lang3.StringUtils;

/**
 * @author 
 * @package com.anson.school_books_system.utils
 * @createTime
 * @description
 */
public class PasswordUtil {

    /**
     * 加密
     *
     * @param password 明文
     * @return
     */
    public static String encryptPassword(String password) {
        if (StringUtils.isBlank(password)) {
            return null;
        }
        return MD5.create().digestHex(password);
    }


    /**
     * 密碼校驗
     *
     * @param password        明文
     * @param encryptPassword 密文
     * @return
     */
    public static boolean checkPassword(String password, String encryptPassword) {
        if (StringUtils.isBlank(password)) {
            throw new SysException("密碼不能為空");
        }
        return encryptPassword(password).equals(encryptPassword);
    }

    public static void main(String[] args) {
        String encryptPassword = encryptPassword("123456");
        System.out.println(encryptPassword);
        System.out.println(checkPassword("123456", encryptPassword));
    }
}
