package com.liga.libraryapi.service;

import com.liga.libraryapi.data.entity.Review;
import com.liga.libraryapi.web.dto.ReviewDto;

import java.util.List;

public interface ReviewService {
    Review createReview(Long bookId, String author, ReviewDto dto);

    Review updateReview(String author, ReviewDto dto);

    void deleteReview(String author);

    void deleteReview(Long authorId);

    List<Review> getAllReviewsByBook(Long bookId);
}
