package com.liga.libraryapi.service;

import com.liga.libraryapi.data.entity.OfferedBook;
import com.liga.libraryapi.data.entity.Rating;
import com.liga.libraryapi.data.entity.RealBook;
import com.liga.libraryapi.data.repository.RealBookRepository;
import com.liga.libraryapi.service.impl.RealBookServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

public class RealBookServiceImplTest {
    @Mock
    private RealBookRepository realBookRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIsExist_True() {
        Long id = 1L;

        Mockito.when(realBookRepository.existsById(id)).thenReturn(true);

        RealBookService realBookService = new RealBookServiceImpl(realBookRepository);

        boolean trueResult = realBookService.isExists(id);

        Assertions.assertTrue(trueResult);

        Mockito.verify(realBookRepository).existsById(id);
    }

    @Test
    public void testIsExist_False() {
        Long id = 1L;

        Mockito.when(realBookRepository.existsById(id)).thenReturn(false);

        RealBookService realBookService = new RealBookServiceImpl(realBookRepository);

        boolean falseResult = realBookService.isExists(id);

        Assertions.assertFalse(falseResult);

        Mockito.verify(realBookRepository).existsById(id);
    }

    @Test
    public void testGetByOldBooksId_Success() {
        Long id = 1L;
        RealBook expected = new RealBook();
        expected.setId(id);

        Mockito.when(realBookRepository.findByOldBookId(id))
                .thenReturn(Optional.of(expected));

        RealBookService realBookService = new RealBookServiceImpl(realBookRepository);

        RealBook realBook = realBookService.getByOldBookId(id);

        Assertions.assertNotNull(realBook);

        Assertions.assertEquals(expected.getOldBookId(), realBook.getOldBookId());

        Mockito.verify(realBookRepository).findByOldBookId(id);
    }

    @Test
    public void testGetByOldBooksId_NotFound_ShouldThrowException() {
        Long id = 1L;

        Mockito.when(realBookRepository.findByOldBookId(id))
                .thenReturn(Optional.empty());

        RealBookService realBookService = new RealBookServiceImpl(realBookRepository);

        IllegalStateException exception = Assertions.assertThrows(
                IllegalStateException.class,
                () -> realBookService.getByOldBookId(id)
        );

        Assertions.assertEquals("book not found", exception.getMessage());

        Mockito.verify(realBookRepository).findByOldBookId(id);
    }

    @Test
    public void testGetBookById_Success() {
        Long id = 1L;
        RealBook expected = new RealBook();
        expected.setId(id);

        Mockito.when(realBookRepository.findById(id))
                .thenReturn(Optional.of(expected));

        RealBookService realBookService = new RealBookServiceImpl(realBookRepository);

        RealBook realBook = realBookService.getBook(id);

        Assertions.assertNotNull(realBook);

        Assertions.assertEquals(expected.getId(), realBook.getId());

        Mockito.verify(realBookRepository).findById(id);
    }

    @Test
    public void testGetBookById_NotFound_ShouldThrowException() {
        Long id = 1L;

        Mockito.when(realBookRepository.findById(id))
                .thenReturn(Optional.empty());

        RealBookService realBookService = new RealBookServiceImpl(realBookRepository);

        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> realBookService.getBook(id)
        );

        Assertions.assertEquals("book not found", exception.getMessage());

        Mockito.verify(realBookRepository).findById(id);
    }

    @Test
    public void testCreate_Success() {
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

        Mockito.when(realBookRepository.save(Mockito.any(RealBook.class))).thenAnswer(invocation -> invocation.getArgument(0));

        RealBookService realBookService = new RealBookServiceImpl(realBookRepository);

        RealBook realBook = realBookService.createBook(offeredBook);

        Assertions.assertNotNull(realBook);

        Assertions.assertEquals("1", realBook.getImageUrl());
        Assertions.assertEquals("2", realBook.getIsbn());
        Assertions.assertEquals("3", realBook.getIsbn13());
        Assertions.assertEquals("4", realBook.getName());
        Assertions.assertEquals("5", realBook.getTitle());
        Assertions.assertEquals("6", realBook.getOriginalTitle());
        Assertions.assertEquals("7", realBook.getLangCode());
        Assertions.assertEquals("8", realBook.getOriginalPublicationYear());
        Assertions.assertEquals("9", realBook.getSmallImageUrl());
        Assertions.assertEquals(BigDecimal.ZERO, realBook.getRatingAvg());
        Assertions.assertEquals(0L, realBook.getRatingCount());

        Mockito.verify(realBookRepository).save(Mockito.any(RealBook.class));
    }

    @Test
    public void testAddRating_Success() {
        Rating rating = Rating.FIVE;
        RealBook realBook = new RealBook();
        realBook.setRatingAvg(BigDecimal.valueOf(2));
        realBook.setRatingCount(2L);

        RealBookService realBookService = new RealBookServiceImpl(realBookRepository);

        realBookService.addRating(rating, realBook);

        Assertions.assertEquals(BigDecimal.valueOf(3).setScale(1, RoundingMode.HALF_UP),
                realBook.getRatingAvg());
        Assertions.assertEquals(3, realBook.getRatingCount());

        Mockito.verify(realBookRepository).saveAndFlush(realBook);
    }

    @Test
    public void testUpdateRating_Success() {
        RealBook realBook = new RealBook();
        realBook.setRatingAvg(BigDecimal.valueOf(3));
        realBook.setRatingCount(2L);
        Rating oldRating = Rating.THREE;
        Rating newRating = Rating.ONE;

        RealBookService realBookService = new RealBookServiceImpl(realBookRepository);

        realBookService.updateReview(oldRating, newRating, realBook);

        Assertions.assertEquals(BigDecimal.valueOf(2).setScale(1, RoundingMode.HALF_UP),
                realBook.getRatingAvg());
        Assertions.assertEquals(2, realBook.getRatingCount());

        Mockito.verify(realBookRepository).saveAndFlush(realBook);
    }

    @Test
    public void testDeleteRating_Success() {
        Rating rating = Rating.THREE;
        RealBook realBook = new RealBook();
        realBook.setRatingAvg(BigDecimal.valueOf(4));
        realBook.setRatingCount(2L);

        RealBookService realBookService = new RealBookServiceImpl(realBookRepository);

        realBookService.deleteRating(rating, realBook);

        Assertions.assertEquals(BigDecimal.valueOf(5).setScale(1, RoundingMode.HALF_UP),
                realBook.getRatingAvg());
        Assertions.assertEquals(1, realBook.getRatingCount());

        Mockito.verify(realBookRepository).saveAndFlush(realBook);
    }

    @Test
    public void testDeleteRating_IfLast_Success() {
        Rating rating = Rating.FIVE;
        RealBook realBook = new RealBook();
        realBook.setRatingAvg(BigDecimal.valueOf(4));
        realBook.setRatingCount(1L);

        RealBookService realBookService = new RealBookServiceImpl(realBookRepository);

        realBookService.deleteRating(rating, realBook);

        Assertions.assertEquals(BigDecimal.valueOf(0).setScale(1, RoundingMode.HALF_UP),
                realBook.getRatingAvg());
        Assertions.assertEquals(0, realBook.getRatingCount());

        Mockito.verify(realBookRepository).saveAndFlush(realBook);
    }
}
