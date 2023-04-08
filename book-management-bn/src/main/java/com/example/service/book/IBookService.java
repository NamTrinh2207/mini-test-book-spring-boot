package com.example.service.book;

import com.example.model.Book;
import com.example.service.IGenericService;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IBookService extends IGenericService<Book> {
    List<Book> findAllByNameContainingIgnoreCase(String name);

    List<Book> findAllByAuthorContainingIgnoreCase(String author);

    List<Book> findByPriceBetween(Long minPrice, Long maxPrice);

    Long getTotalPrice();
}
