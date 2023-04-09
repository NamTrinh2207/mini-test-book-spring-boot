package com.example.controller;

import com.example.model.Book;
import com.example.service.book.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/books")
public class BookController {
    @Autowired
    private IBookService bookService;

    //shows all book
    @GetMapping
    public ResponseEntity<List<Book>> showAllBook() {
        return new ResponseEntity<>((List<Book>) bookService.getAll(), HttpStatus.OK);
    }

    //save book
    @PostMapping
    public ResponseEntity<Book> save(@RequestBody Book book) {
        return new ResponseEntity<>(bookService.save(book), HttpStatus.CREATED);
    }

    //update book
    @PutMapping("/{id}")
    public ResponseEntity<Book> edit(@PathVariable Long id, @RequestBody Book book) {
        Optional<Book> bookOptional = bookService.getById(id);
        if (!bookOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        book.setId(id);
        bookService.save(book);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    //delete book
    @DeleteMapping("/{id}")
    public ResponseEntity<Book> delete(@PathVariable Long id) {
        Optional<Book> bookOptional = bookService.getById(id);
        if (!bookOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        bookService.delete(bookOptional.get().getId());
        return new ResponseEntity<>(bookOptional.get(), HttpStatus.OK);
    }

    // search by name, author and price
    @GetMapping("/search")
    public ResponseEntity<List<Book>> search(
            @RequestParam(value = "name") Optional<String> name,
            @RequestParam(value = "author") Optional<String> author,
            @RequestParam(value = "minPrice", required = false) Optional<Long> minPrice,
            @RequestParam(value = "maxPrice", required = false) Optional<Long> maxPrice) {
        List<Book> books;
        if (name.isPresent() & author.isPresent() & (minPrice.isPresent() || maxPrice.isPresent())) {
            books = bookService.findAllByNameContainingIgnoreCaseAndAuthorContainingIgnoreCaseOrPriceIsBetween(
                    name.orElse("").trim().toLowerCase(),
                    author.orElse("").trim().toLowerCase(),
                    minPrice.orElse(0L),
                    maxPrice.orElse(0L));
        } else {
            books = (List<Book>) bookService.getAll();
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    // get total book price
    @GetMapping("/total-price")
    public ResponseEntity<Long> getTotalPrice() {
        Long totalPrice = bookService.getTotalPrice();
        return ResponseEntity.ok(totalPrice);
    }

    // get book detail
    @GetMapping("/{id}")
    public ResponseEntity<Book> getOne(@PathVariable Long id) {
        Optional<Book> bookOptional = bookService.getById(id);
        return bookOptional.map(book
                -> new ResponseEntity<>(book, HttpStatus.OK)).orElseGet(()
                -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
