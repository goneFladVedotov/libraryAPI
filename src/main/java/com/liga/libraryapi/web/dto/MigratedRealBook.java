package com.liga.libraryapi.web.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MigratedRealBook {
    private Long id;
    private Long book_id;
    private Long best_book_id;
    private Long work_id;
    private Long books_count;
    private String isbn;
    private String isbn13;
    private String authors;
    private String original_publication_year;
    private String original_title;
    private String title;
    private String language_code;
    private BigDecimal average_rating;
    private Long ratings_count;
    private Long work_ratings_count;
    private Long work_text_reviews_count;
    private Long ratings_1;
    private Long ratings_2;
    private Long ratings_3;
    private Long ratings_4;
    private Long ratings_5;
    private String image_url;
    private String small_image_url;
}
