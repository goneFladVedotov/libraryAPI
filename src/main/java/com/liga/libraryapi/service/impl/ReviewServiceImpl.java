package com.liga.libraryapi.service.impl;

import com.liga.libraryapi.data.entity.Review;
import com.liga.libraryapi.data.repository.ReviewRepository;
import com.liga.libraryapi.service.PersonService;
import com.liga.libraryapi.service.RealBookService;
import com.liga.libraryapi.service.ReviewService;
import com.liga.libraryapi.web.dto.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final RealBookService realBookService;
    private final PersonService personService;

    @Transactional
    @Override
    public Review createReview(Long bookId, String author, ReviewDto dto) {
        var person = personService.getByName(author);
        var book = realBookService.getBook(bookId);
        if (reviewRepository.existsByAuthorIdAndBookId(person.getId(), book.getId())) {
            throw new IllegalArgumentException("review already exists");
        }
        Review review = new Review();
        review.setComment(dto.getComment());
        review.setRating(dto.getRating());
        review.setAuthor(author);
        review.setBook(book);

        realBookService.addRating(review.getRating(), book);

        return reviewRepository.save(review);
    }

    @Transactional
    @Override
    public Review updateReview(String author, ReviewDto dto) {
        var person = personService.getByName(author);
        var reviewToUpdate = reviewRepository.findByAuthorId(person.getId())
                .orElseThrow(() -> new IllegalStateException("review not found"));
        var oldRating = reviewToUpdate.getRating();
        reviewToUpdate.setComment(dto.getComment());
        reviewToUpdate.setRating(dto.getRating());
        realBookService.updateReview(oldRating, dto.getRating(), reviewToUpdate.getBook());
        return reviewRepository.saveAndFlush(reviewToUpdate);
    }

    @Transactional
    @Override
    public void deleteReview(String author) {
        var person = personService.getByName(author);
        deleteReview(person.getId());
    }

    @Transactional
    @Override
    public void deleteReview(Long authorId) {
        var review = reviewRepository.findByAuthorId(authorId)
                .orElseThrow(() -> new IllegalStateException("review not found"));
        realBookService.deleteRating(review.getRating(), review.getBook());
        reviewRepository.deleteById(review.getId());
    }

    @Override
    public List<Review> getAllReviewsByBook(Long bookId) {
        return reviewRepository.findAllByBookId(bookId);
    }
}
