package com.anson.school_books_system.api.query;


import cn.hutool.core.util.StrUtil;
import com.anson.school_books_system.common.exception.SysException;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;


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

    /**
     * 需要進行排序的字段
     */
    private String column;
    /**
     * 自然排序(正序)：由小到大，asc，“-” 號開頭：true
     * 倒序：由大到小，desc，“+”號開頭：false
     */
    private boolean asc = true;

    /**
     * 設置排序字段及排序規則
     * @param column +createTime 或 -createTime 等
     */
    public void setColumn(String column) {
        if (StringUtils.isBlank(column)) {
            return;
        }
        if (column.startsWith("+")) {
            this.asc = false;
        }
        // 駝峰轉下劃線
        this.column = StrUtil.toUnderlineCase(column.substring(1));
    }

    public void setSize(Long size) {
        if (size > 50) {
            throw new SysException("每頁最大顯示條目為 50！");
        }
        this.size = size;
    }

    /**
     * 根據分頁參數，生成 MyBatisPlus 分頁對象
     * @param <E>
     * @return
     */
    public <E> Page<E> getPage() {
        Page<E> page = new Page<>(current, size);
        if (StringUtils.isNotBlank(column)) {
            // 排序
            OrderItem orderItem = new OrderItem();
            orderItem.setAsc(asc);
            orderItem.setColumn(column);
            page.setOrders(Collections.singletonList(orderItem));
        }
        return page;
    }
}
