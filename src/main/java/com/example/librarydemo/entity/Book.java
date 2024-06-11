package com.example.librarydemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @version 1.0
 * @description: TODO
 * @author: 12041
 * @date: 2024/6/9 13:05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("books")
public class Book implements Serializable {
    private static final long serialVersionUID = 1L; // 定义一个常量，用于实现序列化
    // 主键
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    // 书名
    private String title;
    // 作者
    private String author;
    // 价格
    private Long price;
    // 类型
    private String category;
}
