package com.example.controller;

import com.example.model.Book;
import com.example.service.book.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
        return ResponseEntity.ok(book);

    }

    //delete book
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Book> bookOptional = bookService.getById(id);
        if (!bookOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        bookService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // search by name, author and price
    @GetMapping("/search")
    public ResponseEntity<List<Book>> search(
            @RequestParam(value = "name", required = false) Optional<String> name,
            @RequestParam(value = "author", required = false) Optional<String> author,
            @RequestParam(value = "maxPrice", required = false) Long maxPrice) {
        if (name.isPresent()) {
            return ResponseEntity.ok(bookService.findAllByNameContainingIgnoreCase(name.get().trim()));
        } else if (author.isPresent()) {
            return ResponseEntity.ok(bookService.findAllByAuthorContainingIgnoreCase(author.get().trim()));
        } else if (maxPrice != null) {
            return ResponseEntity.ok(bookService.findByPriceBetween(0L, maxPrice));
        } else {
            return ResponseEntity.ok((List<Book>) bookService.getAll());
        }
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
