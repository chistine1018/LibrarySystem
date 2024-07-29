package com.anson.school_books_system.common.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class ResultInfo implements Serializable {

    /**
     * 成功與失敗
     */
    protected Boolean result;
    /**
     * 狀態碼
     */
    protected Integer code;
    /**
     * message = null , 不序列化出去
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected String message;

    protected ResultInfo(Boolean result, Integer code, String message) {
        this.result = result;
        this.code = code;
        this.message = message;
    }
}
