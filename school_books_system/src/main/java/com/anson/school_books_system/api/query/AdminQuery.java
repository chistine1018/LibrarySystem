package com.anson.school_books_system.api.query;

import lombok.Data;

/**
 * @author <a href=""></a>
 * @package com.anson.school_books_system.api.query
 * @createTime
 * @description
 */
@Data
public class AdminQuery extends PageQuery{
    /**
     * 暱稱
     */
    private String nickName;

    /**
     * 帳號
     */
    private String username;
}
