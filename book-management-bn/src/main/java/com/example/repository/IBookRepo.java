package com.example.repository;

import com.example.model.Book;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBookRepo extends PagingAndSortingRepository<Book, Long> {
    List<Book> findAllByNameContainingIgnoreCase(String name);

    List<Book> findAllByAuthorContainingIgnoreCase(String author);

    List<Book> findByPriceBetween(Long minPrice, Long maxPrice);
}
