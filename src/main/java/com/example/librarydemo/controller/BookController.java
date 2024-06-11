package com.example.librarydemo.controller;

import com.example.librarydemo.dto.Result;
import com.example.librarydemo.entity.Book;
import com.example.librarydemo.service.IBookService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @version 1.0
 * @description: TODO
 * @author: 12041
 * @date: 2024/6/9 16:40
 */
@RestController
@RequestMapping("/book")
public class BookController {
    @Resource
    public IBookService bookService;

    // 根据id查询
    @GetMapping("/{id}")
    public Result queryBookById(@PathVariable("id") Long id) {
        return bookService.queryBookById(id);
    }

    // 根据名字查询
    @GetMapping("/title")
    public Result queryBookByTitle(@RequestParam("title") String title) {
        return bookService.queryBookByTitle(title);
    }

    // 根据作者查询
    @GetMapping("/author")
    public Result queryBookByAuthor(@RequestParam("author") String author) {
        return bookService.queryBookByAuthor(author);
    }

    // 根据类型查询
    @GetMapping("/category")
    public Result queryBookByCategory(@RequestParam("category") String category) {
        return bookService.queryBookByCategory(category);
    }

    // 更新图书
    @PutMapping
    public Result updateBook(@RequestBody Book book){
        return bookService.updateBook(book);
    }

    // 添加图书
    @PostMapping
    public Result addBook(@RequestBody Book book){
        return bookService.addBook(book);
    }

    // 删除图书
    @DeleteMapping
    public Result deleteByTitle(@RequestBody Book book){
        return bookService.deleteById(book);
    }

}
