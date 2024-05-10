package com.liga.libraryapi.web.dto;

import lombok.Data;

@Data
public class BookDto {
    private String imageUrl;
    private String isbn;
    private String isbn13;
    private String name;
    private String originalPublicationYear;
    private String originalTitle;
    private String smallImageUrl;
    private String title;
    private String langCode;
}
