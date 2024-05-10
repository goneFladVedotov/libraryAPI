package com.liga.libraryapi.web.controller.impl;

import com.liga.libraryapi.data.entity.RealBook;
import com.liga.libraryapi.service.RealBookService;
import com.liga.libraryapi.web.controller.RealBookController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library-api/v1/book/real")
@RequiredArgsConstructor
public class RealBookControllerImpl implements RealBookController {
    private final RealBookService realBookService;

    @GetMapping("/{bookId}")
    @Override
    public ResponseEntity<RealBook> getBook(@PathVariable Long bookId) {
        return ResponseEntity.ok(realBookService.getBook(bookId));
    }

    @GetMapping
    @Override
    public ResponseEntity<List<RealBook>> getBooks() {
        return ResponseEntity.ok(realBookService.getBooks());
    }

    @GetMapping("/filter/name")
    @Override
    public ResponseEntity<List<RealBook>> getBoosByName(@RequestParam String name) {
        return ResponseEntity.ok(realBookService.getBookByName(name));
    }

    @GetMapping("/filter/isbn")
    @Override
    public ResponseEntity<List<RealBook>> getBookByIsbn(@RequestParam String isbn) {
        return ResponseEntity.ok(realBookService.getBookByIsbn(isbn));
    }

    @GetMapping("/sort/name")
    @Override
    public ResponseEntity<List<RealBook>> getSortedBooksByName() {
        return ResponseEntity.ok(realBookService.getSortedBooksByName());
    }

    @GetMapping("/sort/isbn")
    @Override
    public ResponseEntity<List<RealBook>> getSortedBooksByIsbn() {
        return ResponseEntity.ok(realBookService.getSortedBooksByIsbn());
    }
}
