package com.example.librarydemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.librarydemo.dto.Result;
import com.example.librarydemo.entity.Book;

/**
 * @version 1.0
 * @description: TODO
 * @author: 12041
 * @date: 2024/6/9 15:04
 */
public interface IBookService extends IService<Book> {
    Result queryBookById(Long id); // 根据id查询
    Result queryBookByTitle(String title); // 根据名字查询
    Result queryBookByAuthor(String author); // 根据作者查询
    Result queryBookByCategory(String category); // 根据类型查询

    Result updateBook(Book book); // 更新图书

    Result addBook(Book book); // 添加图书

    Result deleteById(Book book); // 删除图书
}
