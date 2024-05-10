package com.liga.libraryapi.config;

import com.liga.libraryapi.data.entity.Rating;
import com.liga.libraryapi.data.entity.Review;
import com.liga.libraryapi.data.repository.RealBookRepository;
import com.liga.libraryapi.service.RealBookService;
import com.liga.libraryapi.web.dto.MigratedReview;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;

@RequiredArgsConstructor
public class MigrationReviewProcessor implements ItemProcessor<MigratedReview, Review> {
    private final RealBookService realBookService;

    @Override
    public Review process(MigratedReview item) throws Exception {
        var book = realBookService.getByOldBookId(item.getBook_id());
        if (book == null) {
            return null;
        }
        Review review = new Review();
        review.setBook(book);
        review.setAuthor("unknown");
        review.setRating(Rating.getRating(item.getRating()));
        realBookService.addRating(Rating.getRating(item.getRating()), book);
        return review;
    }
}
