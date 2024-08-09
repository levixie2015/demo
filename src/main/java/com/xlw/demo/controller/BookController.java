package com.xlw.demo.controller;

import com.xlw.demo.entity.Book;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/book")
public class BookController {
    @RequestMapping("/books")
    public List<Book> getBooks() {
        List<Book> books = new ArrayList<Book>();

        Book book1 = new Book();
        book1.setBookId(1);
        book1.setBookTitle("C语言");
        book1.setBookAuthor("C1");
        book1.setBookPrice(3.00);

        Book book2 = new Book();
        book2.setBookId(2);
        book2.setBookTitle("java语言");
        book2.setBookAuthor("j2");
        book2.setBookPrice(3.00);

        Book book3 = new Book();
        book3.setBookId(3);
        book3.setBookTitle("go语言111111111111111111111111111111111111111");
        book3.setBookAuthor("g3");
        book3.setBookPrice(3.00);

        books.add(book1);
        books.add(book2);
        books.add(book3);
        return books;
    }
}