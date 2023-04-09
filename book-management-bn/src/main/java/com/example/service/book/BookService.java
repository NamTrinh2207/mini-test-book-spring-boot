package com.example.service.book;

import com.example.model.Book;
import com.example.repository.IBookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService implements IBookService {
    @Autowired
    private IBookRepo bookRepo;

    @Override
    public Iterable<Book> getAll() {
        return bookRepo.findAll();
    }

    @Override
    public Optional<Book> getById(Long id) {
        return bookRepo.findById(id);
    }

    @Override
    public Book save(Book book) {
        return bookRepo.save(book);
    }

    @Override
    public void delete(Long id) {
        bookRepo.deleteById(id);
    }

    @Override
    public List<Book> findByPriceIsBetween(Long minPrice, Long maxPrice) {
        return bookRepo.findByPriceIsBetween(minPrice, maxPrice);
    }

    @Override
    public List<Book> findAllByNameContainingIgnoreCaseAndAuthorContainingIgnoreCaseOrPriceIsBetween(String name, String author, Long minPrice, Long maxPrice) {
        return bookRepo.findAllByNameContainingIgnoreCaseAndAuthorContainingIgnoreCaseOrPriceIsBetween(name, author, 0L, maxPrice);
    }

    @Override
    public Long getTotalPrice() {
        List<Book> books = (List<Book>) bookRepo.findAll();
        Long totalPrice = 0L;
        for (Book b : books) {
            totalPrice += b.getPrice();
        }
        return totalPrice;
    }
}
