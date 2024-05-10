package com.liga.libraryapi.web.dto;

import lombok.Data;

@Data
public class MigratedReview {
    private Long book_id;
    private Long user_id;
    private Integer rating;
}
