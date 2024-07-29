package com.anson.school_books_system.common.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author Anson（about：）
 * @description
 */
@Accessors(chain = true)
@ToString
@Getter
public class FailInfo extends ResultInfo{

    /**
     * 失敗對象的默認值
     */
    protected static final Integer DEFAULT_CODE = 500;
    protected static final String DEFAULT_MESSAGE = "操作失败";

    /**
     * 失敗信息
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String exception;


    /**
     * 構造函數
     *
     * @param exception 失敗信息
     */
    public FailInfo(String exception) {
        super(false, DEFAULT_CODE, DEFAULT_MESSAGE);
        this.exception = exception;
    }

    /**
     * 構造函數
     *
     * @param code      失敗code
     * @param exception 失敗信息
     */
    public FailInfo(Integer code, String exception) {
        super(false, code, DEFAULT_MESSAGE);
        this.exception = exception;
    }
}
