package com.liga.libraryapi.service;

import com.liga.libraryapi.data.entity.OfferedBook;
import com.liga.libraryapi.data.entity.RealBook;
import com.liga.libraryapi.data.repository.OfferedBookRepository;
import com.liga.libraryapi.service.impl.OfferedBookMapper;
import com.liga.libraryapi.service.impl.OfferedBookServiceImpl;
import com.liga.libraryapi.web.dto.BookDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class OfferedBookServiceImplTest {
    private final OfferedBookMapper mapper = new OfferedBookMapper();

    @Mock
    private OfferedBookRepository repository;
    @Mock
    private RealBookService realBookService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testOfferBook() {
        String username = "user";
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

        Mockito.when(repository.save(Mockito.any(OfferedBook.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        OfferedBookService offeredBookService = new OfferedBookServiceImpl(repository, realBookService, mapper);
        OfferedBook offeredBook = offeredBookService.offerBook(username, dto);

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
        Assertions.assertEquals(username, offeredBook.getPersonName());

        Mockito.verify(repository).save(Mockito.any(OfferedBook.class));
    }

    @Test
    public void testUpdateOfferedBook_Success() {
        Long id = 1L;
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

        OfferedBook offeredBook = new OfferedBook();
        offeredBook.setFeedback("feedback");

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(offeredBook));
        Mockito.when(repository.saveAndFlush(Mockito.any(OfferedBook.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        OfferedBookService offeredBookService = new OfferedBookServiceImpl(repository, realBookService, mapper);

        OfferedBook result = offeredBookService.updateOfferedBook(id, dto);

        Assertions.assertNotNull(result);

        Assertions.assertEquals("1", result.getImageUrl());
        Assertions.assertEquals("2", result.getIsbn());
        Assertions.assertEquals("3", result.getIsbn13());
        Assertions.assertEquals("4", result.getName());
        Assertions.assertEquals("5", result.getTitle());
        Assertions.assertEquals("6", result.getOriginalTitle());
        Assertions.assertEquals("7", result.getLangCode());
        Assertions.assertEquals("8", result.getOriginalPublicationYear());
        Assertions.assertEquals("9", result.getSmallImageUrl());
        Assertions.assertNull(result.getFeedback());

        Mockito.verify(repository).findById(id);
        Mockito.verify(repository).saveAndFlush(Mockito.any(OfferedBook.class));
    }

    @Test
    public void testUpdateOfferedBook_NotFound_ShouldThrowException() {
        Long id = 1L;
        BookDto dto = new BookDto();

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        OfferedBookService offeredBookService = new OfferedBookServiceImpl(repository, realBookService, mapper);

        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> offeredBookService.updateOfferedBook(id, dto)
        );

        Assertions.assertEquals("offered book not found", exception.getMessage());
    }

    @Test
    public void testCreateFeedback_Success() {
        Long id = 1L;
        String feedback = "feedback";
        OfferedBook offeredBook = new OfferedBook();

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(offeredBook));
        Mockito.when(repository.saveAndFlush(Mockito.any(OfferedBook.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        OfferedBookService offeredBookService = new OfferedBookServiceImpl(repository, realBookService, mapper);

        OfferedBook result = offeredBookService.createFeedback(id, feedback);

        Assertions.assertNotNull(result);

        Assertions.assertEquals("feedback", result.getFeedback());

        Mockito.verify(repository).findById(id);
        Mockito.verify(repository).saveAndFlush(Mockito.any(OfferedBook.class));
    }

    @Test
    public void testCreateFeedback_OfferedBookNotFound_ShouldThrowException() {
        Long id = 1L;
        String feedback = "feedback";

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        OfferedBookService offeredBookService = new OfferedBookServiceImpl(repository, realBookService, mapper);

        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> offeredBookService.createFeedback(id, feedback)
        );

        Assertions.assertEquals("offered book not found", exception.getMessage());

        Mockito.verify(repository).findById(id);
    }

    @Test
    public void testConfirmOffer_Success() {
        Long id = 1L;
        OfferedBook offeredBook = new OfferedBook();
        RealBook realBook = new RealBook();

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(offeredBook));
        Mockito.when(realBookService.createBook(Mockito.any(OfferedBook.class)))
                .thenReturn(realBook);

        OfferedBookService offeredBookService = new OfferedBookServiceImpl(repository, realBookService, mapper);

        RealBook result = offeredBookService.confirmOffer(id);

        Assertions.assertNotNull(result);

        Mockito.verify(repository).findById(id);
        Mockito.verify(realBookService).createBook(Mockito.any(OfferedBook.class));
        Mockito.verify(repository).deleteById(id);
    }

    @Test
    public void testConfirmOffered_OfferedBookNotFound_ShouldThrowException() {
        Long id = 1L;

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        OfferedBookService offeredBookService = new OfferedBookServiceImpl(repository, realBookService, mapper);

        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> offeredBookService.confirmOffer(id)
        );

        Assertions.assertEquals("offered book not found", exception.getMessage());

        Mockito.verify(repository).findById(id);
    }
}
