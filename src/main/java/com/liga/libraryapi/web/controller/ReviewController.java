package com.liga.libraryapi.web.controller;

import com.liga.libraryapi.data.entity.Review;
import com.liga.libraryapi.web.dto.ReviewDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReviewController {
    ResponseEntity<Review> create(Long bookId, ReviewDto reviewDto);

    ResponseEntity<List<Review>> getReviewsByBook(Long bookId);

    ResponseEntity<Review> update(ReviewDto reviewDto);

    void delete();

    void delete(Long authorId);
}
