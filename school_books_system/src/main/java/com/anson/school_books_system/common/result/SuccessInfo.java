package com.anson.school_books_system.common.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author <a href=""></a>
 * @package com.anson.school_books_system.common.result
 * @createTime 2024/3/10 - 23:14
 * @description
 */
@Accessors(chain = true)
@ToString
@Getter
public class SuccessInfo extends ResultInfo{

    /**
     * 成功對象的默認值
     */
    protected static final Integer DEFAULT_CODE = 200;
    protected static final String DEFAULT_MESSAGE = "操作成功";

    /**
     * 结果數據
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected Object data;

    protected SuccessInfo(Object data) {
        super(true, DEFAULT_CODE, DEFAULT_MESSAGE);
        this.data = data;
    }
}
