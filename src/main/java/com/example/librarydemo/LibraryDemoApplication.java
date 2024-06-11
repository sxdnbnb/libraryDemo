package com.example.librarydemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.example.librarydemo.mapper")
@SpringBootApplication
public class LibraryDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryDemoApplication.class, args);
    }

}
