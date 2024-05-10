package com.liga.libraryapi.web.controller;

import com.liga.libraryapi.data.entity.OfferedBook;
import com.liga.libraryapi.data.entity.RealBook;
import com.liga.libraryapi.web.dto.BookDto;
import com.liga.libraryapi.web.dto.FeedbackDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OfferedBookController {
    ResponseEntity<OfferedBook> offerBook(BookDto dto);

    ResponseEntity<OfferedBook> updateOfferedBook(Long offeredBookId, BookDto dto);

    ResponseEntity<List<OfferedBook>> getAllOfferedBooks();

    ResponseEntity<List<OfferedBook>> getAllOfferedBooksByPerson();

    ResponseEntity<OfferedBook> createFeedback(Long offeredBookId, FeedbackDto dto);

    ResponseEntity<RealBook> confirmOffer(Long offeredBookId);
}
