package com.liga.libraryapi.service.impl;

import com.liga.libraryapi.data.entity.OfferedBook;
import com.liga.libraryapi.data.entity.RealBook;
import com.liga.libraryapi.data.repository.OfferedBookRepository;
import com.liga.libraryapi.service.Mapper;
import com.liga.libraryapi.service.OfferedBookService;
import com.liga.libraryapi.service.RealBookService;
import com.liga.libraryapi.web.dto.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OfferedBookServiceImpl implements OfferedBookService {
    private final OfferedBookRepository offeredBookRepository;
    private final RealBookService realBookService;
    private final Mapper<BookDto, OfferedBook> mapper;

    @Override
    public OfferedBook offerBook(String username, BookDto dto) {
        OfferedBook offeredBook = mapper.toEntity(dto);
        offeredBook.setPersonName(username);

        return offeredBookRepository.save(offeredBook);
    }

    @Transactional
    @Override
    public OfferedBook updateOfferedBook(Long offeredBookId, BookDto dto) {
        var offeredBook = offeredBookRepository.findById(offeredBookId)
                .orElseThrow(() -> new IllegalArgumentException("offered book not found"));
        offeredBook = mapper.toEntity(offeredBook, dto);
        offeredBook.setFeedback(null);

        return offeredBookRepository.saveAndFlush(offeredBook);
    }

    @Override
    public List<OfferedBook> getAllOfferedBooks() {
        return offeredBookRepository.findAllByFeedbackIsNull();
    }

    @Override
    public List<OfferedBook> getOfferedBookByPerson(String username) {
        return offeredBookRepository.findAllByPersonName(username);
    }

    @Transactional
    @Override
    public OfferedBook createFeedback(Long offeredBookId, String feedback) {
        var offeredBook = offeredBookRepository.findById(offeredBookId)
                .orElseThrow(() -> new IllegalArgumentException("offered book not found"));
        offeredBook.setFeedback(feedback);
        return offeredBookRepository.saveAndFlush(offeredBook);
    }

    @Transactional
    @Override
    public RealBook confirmOffer(Long offeredBookId) {
        var offeredBook = offeredBookRepository.findById(offeredBookId)
                .orElseThrow(() -> new IllegalArgumentException("offered book not found"));
        var realBook = realBookService.createBook(offeredBook);
        offeredBookRepository.deleteById(offeredBookId);
        return realBook;
    }
}
