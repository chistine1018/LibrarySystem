package com.anson.school_books_system.scheduled;

import com.anson.school_books_system.service.SysTokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@AllArgsConstructor
public class TokenScheduled {

    private final SysTokenService sysTokenService;

    /**
     * 移除過期的 token
     */
    @Scheduled(cron = "15 0/4 * * * ?")
    public void deleteToken() {
        try {
            log.info("刪除過期token開始！");
            sysTokenService.removeExpiredToken(LocalDateTime.now());
            log.info("刪除過期token完成！");
        }catch (Exception e){
            log.error("刪除過期token失败", e);
        }
    }
}
