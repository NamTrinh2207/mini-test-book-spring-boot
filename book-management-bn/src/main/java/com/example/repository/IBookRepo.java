package com.example.repository;

import com.example.model.Book;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBookRepo extends PagingAndSortingRepository<Book, Long> {
    List<Book> findByPriceIsBetween(Long minPrice, Long maxPrice);
    List<Book> findAllByNameContainingIgnoreCaseAndAuthorContainingIgnoreCaseOrPriceIsBetween(String name, String author, Long minPrice, Long maxPrice);
}
