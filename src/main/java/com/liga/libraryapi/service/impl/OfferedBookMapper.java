package com.liga.libraryapi.service.impl;

import com.liga.libraryapi.data.entity.OfferedBook;
import com.liga.libraryapi.service.Mapper;
import com.liga.libraryapi.web.dto.BookDto;
import org.springframework.stereotype.Component;

@Component
public class OfferedBookMapper implements Mapper<BookDto, OfferedBook> {
    @Override
    public OfferedBook toEntity(BookDto dto) {
        OfferedBook offeredBook = new OfferedBook();
        offeredBook.setIsbn(dto.getIsbn());
        offeredBook.setName(dto.getName());
        offeredBook.setIsbn13(dto.getIsbn13());
        offeredBook.setImageUrl(dto.getImageUrl());
        offeredBook.setLangCode(dto.getLangCode());
        offeredBook.setOriginalPublicationYear(dto.getOriginalPublicationYear());
        offeredBook.setOriginalTitle(dto.getOriginalTitle());
        offeredBook.setSmallImageUrl(dto.getSmallImageUrl());
        offeredBook.setTitle(dto.getTitle());
        return offeredBook;
    }

    @Override
    public OfferedBook toEntity(OfferedBook offeredBook, BookDto dto) {
        offeredBook.setIsbn(dto.getIsbn());
        offeredBook.setName(dto.getName());
        offeredBook.setIsbn13(dto.getIsbn13());
        offeredBook.setImageUrl(dto.getImageUrl());
        offeredBook.setLangCode(dto.getLangCode());
        offeredBook.setOriginalPublicationYear(dto.getOriginalPublicationYear());
        offeredBook.setOriginalTitle(dto.getOriginalTitle());
        offeredBook.setSmallImageUrl(dto.getSmallImageUrl());
        offeredBook.setTitle(dto.getTitle());
        return offeredBook;
    }

    @Override
    public BookDto toDto(OfferedBook entity) {
        BookDto dto = new BookDto();
        dto.setIsbn(entity.getIsbn());
        dto.setName(entity.getName());
        dto.setIsbn13(entity.getIsbn13());
        dto.setImageUrl(entity.getImageUrl());
        dto.setLangCode(entity.getLangCode());
        dto.setOriginalPublicationYear(entity.getOriginalPublicationYear());
        dto.setOriginalTitle(entity.getOriginalTitle());
        dto.setSmallImageUrl(entity.getSmallImageUrl());
        dto.setTitle(entity.getTitle());
        return dto;
    }
}
