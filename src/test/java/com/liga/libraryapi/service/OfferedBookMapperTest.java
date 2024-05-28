package com.liga.libraryapi.service;

import com.liga.libraryapi.data.entity.OfferedBook;
import com.liga.libraryapi.service.impl.OfferedBookMapper;
import com.liga.libraryapi.web.dto.BookDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OfferedBookMapperTest {

    @Test
    public void testToEntity_Success() {
        BookDto dto = new BookDto();
        dto.setImageUrl("1");
        dto.setIsbn("2");
        dto.setIsbn13("3");
        dto.setName("4");
        dto.setTitle("5");
        dto.setOriginalTitle("6");
        dto.setLangCode("7");
        dto.setOriginalPublicationYear("8");
        dto.setSmallImageUrl("9");

        OfferedBookMapper mapper = new OfferedBookMapper();
        OfferedBook offeredBook = mapper.toEntity(dto);

        Assertions.assertNotNull(offeredBook);

        Assertions.assertEquals("1", offeredBook.getImageUrl());
        Assertions.assertEquals("2", offeredBook.getIsbn());
        Assertions.assertEquals("3", offeredBook.getIsbn13());
        Assertions.assertEquals("4", offeredBook.getName());
        Assertions.assertEquals("5", offeredBook.getTitle());
        Assertions.assertEquals("6", offeredBook.getOriginalTitle());
        Assertions.assertEquals("7", offeredBook.getLangCode());
        Assertions.assertEquals("8", offeredBook.getOriginalPublicationYear());
        Assertions.assertEquals("9", offeredBook.getSmallImageUrl());
    }

    @Test
    public void testToEntityItself_Success() {
        BookDto dto = new BookDto();
        dto.setImageUrl("1");
        dto.setIsbn("2");
        dto.setIsbn13("3");
        dto.setName("4");
        dto.setTitle("5");
        dto.setOriginalTitle("6");
        dto.setLangCode("7");
        dto.setOriginalPublicationYear("8");
        dto.setSmallImageUrl("9");
        OfferedBook emptyOfferedBook = new OfferedBook();

        OfferedBookMapper mapper = new OfferedBookMapper();
        OfferedBook offeredBook = mapper.toEntity(emptyOfferedBook, dto);

        Assertions.assertNotNull(offeredBook);

        Assertions.assertSame(emptyOfferedBook, offeredBook);

        Assertions.assertEquals("1", offeredBook.getImageUrl());
        Assertions.assertEquals("2", offeredBook.getIsbn());
        Assertions.assertEquals("3", offeredBook.getIsbn13());
        Assertions.assertEquals("4", offeredBook.getName());
        Assertions.assertEquals("5", offeredBook.getTitle());
        Assertions.assertEquals("6", offeredBook.getOriginalTitle());
        Assertions.assertEquals("7", offeredBook.getLangCode());
        Assertions.assertEquals("8", offeredBook.getOriginalPublicationYear());
        Assertions.assertEquals("9", offeredBook.getSmallImageUrl());
    }

    @Test
    public void toDto_Success() {
        OfferedBook offeredBook = new OfferedBook();
        offeredBook.setImageUrl("1");
        offeredBook.setIsbn("2");
        offeredBook.setIsbn13("3");
        offeredBook.setName("4");
        offeredBook.setTitle("5");
        offeredBook.setOriginalTitle("6");
        offeredBook.setLangCode("7");
        offeredBook.setOriginalPublicationYear("8");
        offeredBook.setSmallImageUrl("9");

        OfferedBookMapper mapper = new OfferedBookMapper();
        BookDto bookDto = mapper.toDto(offeredBook);

        Assertions.assertNotNull(bookDto);

        Assertions.assertEquals("1", bookDto.getImageUrl());
        Assertions.assertEquals("2", bookDto.getIsbn());
        Assertions.assertEquals("3", bookDto.getIsbn13());
        Assertions.assertEquals("4", bookDto.getName());
        Assertions.assertEquals("5", bookDto.getTitle());
        Assertions.assertEquals("6", bookDto.getOriginalTitle());
        Assertions.assertEquals("7", bookDto.getLangCode());
        Assertions.assertEquals("8", bookDto.getOriginalPublicationYear());
        Assertions.assertEquals("9", bookDto.getSmallImageUrl());
    }
}
