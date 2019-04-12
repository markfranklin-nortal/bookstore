package com.bookstore.controller;

import com.bookstore.model.Book;
import com.bookstore.repository.BookRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping(path = "/books")
public class BookController {
    
    private final BookRepository bookRepository;
    
    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    @PostMapping
    public Book saveBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }
    
    @PostMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book requestedBook) {
        Book actualBook = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Book with id %s not found!", id)));
        BeanUtils.copyProperties(requestedBook, actualBook, "id");
        return bookRepository.save(actualBook);
    }
    
    @GetMapping
    public List<Book> findAllBooks() {
        return (List<Book>) bookRepository.findAll();
    } 
    
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleEntityNotFound(EntityNotFoundException e) {
        return e.getMessage();
    }
    
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleDataIntegrityConstraintViolation(DataIntegrityViolationException e) {
        return e.getMessage();
    }
}
