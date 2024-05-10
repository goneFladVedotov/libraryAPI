package com.liga.libraryapi.web.controller.impl;

import com.liga.libraryapi.data.entity.Review;
import com.liga.libraryapi.service.ReviewService;
import com.liga.libraryapi.web.controller.ReviewController;
import com.liga.libraryapi.web.dto.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library-api/v1/review")
@RequiredArgsConstructor
public class ReviewControllerImpl implements ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/user")
    @Override
    public ResponseEntity<Review> create(@RequestParam Long bookId, @RequestBody ReviewDto reviewDto) {
        var author = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(reviewService.createReview(bookId, author, reviewDto));
    }

    @GetMapping
    @Override
    public ResponseEntity<List<Review>> getReviewsByBook(@RequestParam Long bookId) {
        return ResponseEntity.ok(reviewService.getAllReviewsByBook(bookId));
    }

    @PutMapping("/user")
    @Override
    public ResponseEntity<Review> update(@RequestBody ReviewDto reviewDto) {
        var author = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(reviewService.updateReview(author, reviewDto));
    }

    @DeleteMapping("/user")
    @Override
    public void delete() {
        var author = SecurityContextHolder.getContext().getAuthentication().getName();
        reviewService.deleteReview(author);
    }

    @DeleteMapping("/admin/{authorId}")
    @Override
    public void delete(@PathVariable Long authorId) {
        reviewService.deleteReview(authorId);
    }
}
