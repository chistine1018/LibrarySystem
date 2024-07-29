package com.anson.school_books_system.service.impl;

import com.alibaba.fastjson.JSON;
import com.anson.school_books_system.api.vo.AuthVO;
import com.anson.school_books_system.utils.SysUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.anson.school_books_system.po.SysToken;
import com.anson.school_books_system.service.SysTokenService;
import com.anson.school_books_system.mapper.SysTokenMapper;
import org.springframework.stereotype.Service;

/**
* @author user
* @description 針對表【sys_token】的數據庫操作Service實現
* @createDate 2024-07-29 20:07:46
*/
@Service
public class SysTokenServiceImpl extends ServiceImpl<SysTokenMapper, SysToken>
    implements SysTokenService{

    @Override
    public String createToken(AuthVO authVO) {
        SysToken sysToken = new SysToken();
        sysToken.setToken(SysUtil.getToken());
        authVO.setToken(sysToken.getToken());
        sysToken.setAuthInfo(JSON.toJSONString(authVO));
        sysToken.setExpiredTime(SysUtil.getTokenExpiredTime());
        save(sysToken);

        return sysToken.getToken();
    }
}




