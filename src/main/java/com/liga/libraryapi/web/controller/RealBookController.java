package com.liga.libraryapi.web.controller;

import com.liga.libraryapi.data.entity.RealBook;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RealBookController {
    ResponseEntity<RealBook> getBook(Long bookId);

    ResponseEntity<List<RealBook>> getBooks();

    ResponseEntity<List<RealBook>> getBoosByName(String name);

    ResponseEntity<List<RealBook>> getBookByIsbn(String isbn);

    ResponseEntity<List<RealBook>> getSortedBooksByName();

    ResponseEntity<List<RealBook>> getSortedBooksByIsbn();
}
