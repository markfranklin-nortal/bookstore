package com.bookstore.repository;


import com.bookstore.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findByAuthor(String author); 
    List<Book> findAllByPrice(float price);
}
