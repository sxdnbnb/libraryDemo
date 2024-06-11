package com.example.librarydemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.librarydemo.dto.Result;
import com.example.librarydemo.entity.Book;
import com.example.librarydemo.mapper.BookMapper;
import com.example.librarydemo.service.IBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @version 1.0
 * @description: TODO
 * @author: 12041
 * @date: 2024/6/9 15:23
 */
// @Slf4j
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements IBookService {

    @Override
    public Result queryBookById(Long id) {
        Book book = getById(id);
        if (book == null){
            return Result.fail("图书不存在");
        }
        // log.debug("图书id为：{}", id);
        return Result.ok(book);
    }

    @Override
    public Result queryBookByTitle(String title) {
        List<Book> books = query().eq("title", title).list();
        if (books == null){
            return Result.fail("图书不存在");
        }
        return Result.ok(books);
    }

    @Override
    public Result queryBookByAuthor(String author) {
        List<Book> books = query().eq("author", author).list();
        if (books == null){
            return Result.fail("图书不存在");
        }
        return Result.ok(books);
    }

    @Override
    public Result queryBookByCategory(String category) {
        List<Book> books = query().eq("category", category).list();
        if (books == null){
            return Result.fail("图书不存在");
        }
        return Result.ok(books);
    }

    @Override
    @Transactional   // 开启事务
    public Result updateBook(Book book) {
        Long id = book.getId();
        if (id == null) {
            return Result.fail("图书id不能为空！");
        }
        // 更新数据库
        updateById(book);
        return Result.ok();
    }

    @Override
    @Transactional   // 开启事务
    public Result addBook(Book book) {
        if (book.getAuthor() == null || book.getTitle() == null || book.getPrice() == null || book.getCategory() == null) {
            return Result.fail("图书信息输入有误");
        }
        // 添加到数据库
        save(book);
        // 返回图书id
        return Result.ok(book.getId());
    }

    @Override
    @Transactional   // 开启事务
    public Result deleteById(Book book) {
        Long id = book.getId();
        if (id == null) {
            return Result.fail("图书不存在");
        }
        // 删除数据库
        removeById(book);
        return Result.ok();
    }
}
