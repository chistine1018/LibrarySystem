package com.anson.school_books_system.api.vo;

import com.anson.school_books_system.po.UserReturnBookInfo;
import lombok.Data;

/**
 * @author
 * @package com.anson.school_books_system.api.vo
 * @createTime
 * @description
 */
@Data
public class UserReturnBookInfoVO extends UserReturnBookInfo {

    /**
     * 學號
     */
    private String studentNumber;

    /**
     * 姓名
     */
    private String name;

    /**
     * 圖書編號
     */
    private String bookNumber;

    /**
     * 封面url
     */
    private String coverUrl;

    /**
     * 書名
     */
    private String bookName;
}
