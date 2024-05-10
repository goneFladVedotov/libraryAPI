package com.liga.libraryapi.service.impl;

import com.liga.libraryapi.data.entity.OfferedBook;
import com.liga.libraryapi.data.entity.Rating;
import com.liga.libraryapi.data.entity.RealBook;
import com.liga.libraryapi.data.repository.RealBookRepository;
import com.liga.libraryapi.service.RealBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RealBookServiceImpl implements RealBookService {
    private final RealBookRepository realBookRepository;

    @Override
    public RealBook createBook(OfferedBook offeredBook) {
        RealBook realBook = new RealBook();
        realBook.setName(offeredBook.getName());
        realBook.setIsbn(offeredBook.getIsbn());
        realBook.setIsbn13(offeredBook.getIsbn13());
        realBook.setLangCode(offeredBook.getLangCode());
        realBook.setImageUrl(offeredBook.getImageUrl());
        realBook.setOriginalPublicationYear(offeredBook.getOriginalPublicationYear());
        realBook.setOriginalTitle(offeredBook.getOriginalTitle());
        realBook.setRatingAvg(BigDecimal.ZERO);
        realBook.setTitle(offeredBook.getTitle());
        realBook.setRatingCount(0L);
        realBook.setSmallImageUrl(offeredBook.getSmallImageUrl());
        return realBookRepository.save(realBook);
    }

    @Override
    public RealBook getBook(Long bookId) {
        return realBookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("book not found"));
    }

    @Override
    public List<RealBook> getBooks() {
        return realBookRepository.findAll();
    }

    @Override
    public List<RealBook> getBookByName(String name) {
        return realBookRepository.findByName(name);
    }

    @Override
    public List<RealBook> getBookByIsbn(String isbn) {
        return realBookRepository.findByIsbn(isbn);
    }

    @Override
    public List<RealBook> getSortedBooksByName() {
        Sort sort = Sort.by(Sort.Direction.ASC, "name");

        return realBookRepository.findAll(sort);
    }

    @Override
    public List<RealBook> getSortedBooksByIsbn() {
        Sort sort = Sort.by(Sort.Direction.ASC, "isbn");
        return realBookRepository.findAll(sort);
    }

    @Override
    public void addRating(Rating rating, RealBook book) {
        var sum = book.getRatingAvg().multiply(new BigDecimal(book.getRatingCount()));
        sum = sum.add(new BigDecimal(rating.ordinal() + 1));
        book.setRatingCount(book.getRatingCount() + 1);
        book.setRatingAvg(sum.divide(new BigDecimal(book.getRatingCount())).setScale(1));
        realBookRepository.saveAndFlush(book);
    }

    @Override
    public void updateReview(Rating oldRating, Rating newRating, RealBook book) {
        var sum = book.getRatingAvg().multiply(new BigDecimal(book.getRatingCount()));
        sum = sum.subtract(new BigDecimal(oldRating.ordinal() + 1));
        sum = sum.add(new BigDecimal(newRating.ordinal() + 1));
        book.setRatingAvg(sum.divide(new BigDecimal(book.getRatingCount())).setScale(1));
        realBookRepository.saveAndFlush(book);
    }

    @Override
    public void deleteRating(Rating rating, RealBook book) {
        var sum = book.getRatingAvg().multiply(new BigDecimal(book.getRatingCount()));
        sum = sum.subtract(new BigDecimal(rating.ordinal() + 1));
        book.setRatingCount(book.getRatingCount() - 1);
        if (book.getRatingCount() == 0) {
            book.setRatingAvg(BigDecimal.ZERO);
        } else {
            book.setRatingAvg(sum.divide(new BigDecimal(book.getRatingCount())));
        }
        realBookRepository.saveAndFlush(book);
    }

    @Override
    public RealBook getByOldBookId(Long oldBookId) {
        return realBookRepository.findByOldBookId(oldBookId);
    }

    @Override
    public boolean isExists(Long bookId) {
        return realBookRepository.existsById(bookId);
    }
}
