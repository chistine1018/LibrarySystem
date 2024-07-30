package com.anson.school_books_system.service;

import com.anson.school_books_system.api.vo.AuthVO;
import com.anson.school_books_system.po.SysToken;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDateTime;

/**
* @author user
* @description 針對表【sys_token】的數據庫操作Service
* @createDate 2024-07-29 20:07:46
*/
public interface SysTokenService extends IService<SysToken> {
    String createToken(AuthVO authVO);

    AuthVO getByToken(String token);

    void removeByToken(String token);

    void removeExpiredToken(LocalDateTime now);
}
