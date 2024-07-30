package com.anson.school_books_system.api.query;


import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author <a href=""></a>
 * @package com.anson.school_books_system.api.query
 * @createTime
 * @description
 */
@Data
public class PageQuery {
    private static final Logger log = LoggerFactory.getLogger(PageQuery.class);
    private Long size = 10L;
    private Long current = 1L;
}
