package com.anson.school_books_system.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 
 * @TableName sys_token
 */
@TableName(value ="sys_token")
@Data
public class SysToken implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * token字串
     */
    private String token;

    /**
     * 認證訊息，json 內容
     */
    private String authInfo;

    /**
     * token的過期時間
     */
    private LocalDateTime expiredTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}