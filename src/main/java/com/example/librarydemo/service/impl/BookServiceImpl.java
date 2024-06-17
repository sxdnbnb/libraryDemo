package com.example.librarydemo.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.librarydemo.dto.Result;
import com.example.librarydemo.entity.Book;
import com.example.librarydemo.mapper.BookMapper;
import com.example.librarydemo.service.IBookService;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.example.librarydemo.utils.RedisConstants.*;

/**
 * @version 1.0
 * @description: TODO
 * @author: 12041
 * @date: 2024/6/9 15:23
 */
// @Slf4j
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements IBookService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    // 使用redis缓存
    @Override
    public Result queryBookById(Long id) {
        String key = CACHE_Book_KEY + id;
        // 1.从redis查询书籍缓存
        String bookJson = stringRedisTemplate.opsForValue().get(key);
        // 2.判断是否存在
        if (StrUtil.isNotBlank(bookJson)) {
            // 3.存在，直接返回
            Book book = JSONUtil.toBean(bookJson, Book.class);
            return Result.ok(book);
        }
        // 判断命中的是否是空值
        if (bookJson != null) {
            // 返回一个错误信息
            return Result.fail("图书信息不存在！");
        }

        // 4.不存在，根据id查询数据库
        Book book = getById(id);
        // 5.不存在，返回错误
        if (book == null) {
            // 将空值写入redis
            stringRedisTemplate.opsForValue().set(key, "", CACHE_NULL_TTL, TimeUnit.MINUTES);
            // 返回错误信息
            return Result.fail("图书不存在！");
        }
        // 6.存在，写入redis
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(book), CACHE_Book_TTL, TimeUnit.MINUTES);
        // 7.返回
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
        // 1.更新数据库
        updateById(book);
        // 2.删除缓存
        stringRedisTemplate.delete(CACHE_Book_KEY + id);
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
        // 1.删除数据库
        removeById(book);
        // 2.删除缓存
        stringRedisTemplate.delete(CACHE_Book_KEY + id);
        return Result.ok();
    }
}
