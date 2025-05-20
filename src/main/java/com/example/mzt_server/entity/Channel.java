package com.example.mzt_server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 栏目表实体类
 * 对应表：channel
 */
@Data
@TableName("channel")
public class Channel implements Serializable {
    /** 栏目ID */
    @TableId(type = IdType.AUTO)
    private Long id;
    /** 栏目名称 */
    private String name;
    /** 栏目描述 */
    private String description;
    /** 创建时间 */
    private Date createtime;
} 