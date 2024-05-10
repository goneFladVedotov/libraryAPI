package com.liga.libraryapi.service;

import com.liga.libraryapi.data.entity.OfferedBook;
import com.liga.libraryapi.data.entity.Rating;
import com.liga.libraryapi.data.entity.RealBook;

import java.util.List;

public interface RealBookService {
    RealBook createBook(OfferedBook offeredBook);

    RealBook getBook(Long bookId);

    List<RealBook> getBooks();

    List<RealBook> getBookByName(String name);

    List<RealBook> getBookByIsbn(String isbn);

    List<RealBook> getSortedBooksByName();

    List<RealBook> getSortedBooksByIsbn();

    void addRating(Rating rating, RealBook book);

    void updateReview(Rating oldRating, Rating newRating, RealBook book);

    void deleteRating(Rating rating, RealBook book);

    RealBook getByOldBookId(Long oldBookId);

    boolean isExists(Long bookId);
}
