package com.liga.libraryapi.web.dto;

import com.liga.libraryapi.data.entity.Rating;
import lombok.Data;

@Data
public class ReviewDto {
    private String comment;
    private final Rating rating;
}
