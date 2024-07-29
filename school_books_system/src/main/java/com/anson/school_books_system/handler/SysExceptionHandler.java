package com.anson.school_books_system.handler;


import com.anson.school_books_system.common.exception.SysException;
import com.anson.school_books_system.common.result.FailInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Anson（about：）
 * @description
 */
@Slf4j
@RestControllerAdvice
public class SysExceptionHandler {

    /**
     * 最大的兜底錯誤處理
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public FailInfo exception(Exception ex) {
        log.error("Exception_info:{}", ex.getMessage());
        log.error("Exception_info:", ex);
        return new FailInfo(ex.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public FailInfo methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("Exception_info:{}", ex.getMessage());
        log.error("Exception_info:", ex);
        return new FailInfo(ex.getMessage());
    }

    /**
     * 我們自己定義的錯誤處理 SysException
     * @param ex
     * @return
     */
    @ExceptionHandler(value = SysException.class)
    public FailInfo sysException(SysException ex) {
        log.error("Exception_info:{}", ex.getMessage());
        log.error("Exception_info:", ex);
        return new FailInfo(ex.getMessage());
    }


    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public FailInfo dataIntegrityViolationException(Exception ex) {
        log.error("Exception_info:{}", ex.getMessage());
        log.error("Exception_info:", ex);
        String message = ex.getMessage();
        String[] split = message.split("\r\n###");
        for (String str : split) {
            if (StringUtils.isBlank(str) || str.trim().contains("Error")) {
                continue;
            }
            String[] split1 = str.split(":");
            if (split1.length > 0) {
                message = split1[split1.length - 1].trim();
            }
        }
        return new FailInfo(500, message);
    }
}
