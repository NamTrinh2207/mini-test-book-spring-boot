package com.example.service.book;

import com.example.model.Book;
import com.example.service.IGenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IBookService extends IGenericService<Book> {

    List<Book> findByPriceIsBetween(Long minPrice, Long maxPrice);
    List<Book> findAllByNameContainingIgnoreCaseAndAuthorContainingIgnoreCaseOrPriceIsBetween(String name, String author, Long minPrice, Long maxPrice);
    Long getTotalPrice();
}
