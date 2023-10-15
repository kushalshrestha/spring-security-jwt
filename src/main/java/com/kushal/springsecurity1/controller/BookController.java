package com.kushal.springsecurity1.controller;

import com.kushal.springsecurity1.model.Book;
import com.kushal.springsecurity1.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping
//    @PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
    public List<Book> getAllBooks() {
        return Arrays.asList(

                Book.builder()
                    .id(1L)
                    .title("Book 1")
                    .author("Author 1")
                    .build(), Book.builder()
                                  .id(2L)
                                  .title("Book 2")
                                  .author("Author 2")
                                  .build(), Book.builder()
                                                .id(1L)
                                                .title("Book 2")
                                                .author("Author 2")
                                                .build());


    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
    public Book getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
    public Book createBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public Book updateBook(
            @PathVariable Long id,
            @RequestBody Book book
    ) {
        return bookService.updateBook(id, book);
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}
