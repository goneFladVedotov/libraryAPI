package com.liga.libraryapi.service;

import com.liga.libraryapi.data.entity.OfferedBook;
import com.liga.libraryapi.data.entity.RealBook;
import com.liga.libraryapi.web.dto.BookDto;

import java.util.List;

public interface OfferedBookService {
    OfferedBook offerBook(String username, BookDto dto);

    OfferedBook updateOfferedBook(Long offeredBookId, BookDto dto);

    List<OfferedBook> getAllOfferedBooks();

    List<OfferedBook> getOfferedBookByPerson(String username);

    OfferedBook createFeedback(Long offeredBookId, String feedback);

    RealBook confirmOffer(Long offeredBookId);
}
