package com.liga.libraryapi;

import com.liga.libraryapi.data.entity.Book;
import com.liga.libraryapi.data.entity.OfferedBook;
import com.liga.libraryapi.data.entity.Rating;
import com.liga.libraryapi.data.entity.RealBook;
import com.liga.libraryapi.data.repository.RealBookRepository;
import com.liga.libraryapi.service.Mapper;
import com.liga.libraryapi.service.OfferedBookService;
import com.liga.libraryapi.service.RealBookService;
import com.liga.libraryapi.service.ReviewService;
import com.liga.libraryapi.web.dto.BookDto;
import com.liga.libraryapi.web.dto.ReviewDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@SpringBootTest
@Transactional
@Rollback
class LibraryApiApplicationTests {
    @Autowired
    private RealBookRepository realBookRepository;
    @Autowired
    private RealBookService realBookService;
    @Autowired
    private OfferedBookService offeredBookService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private Mapper<BookDto, OfferedBook> mapper;


    @Test
    void test1() {
        //realBookRepository.save(createTestBook());

        var allBooks = realBookService.getBooks();
        Assertions.assertNotNull(allBooks);
        Assertions.assertFalse(allBooks.isEmpty());

        String targetName = "test";
        var filteredBooks = realBookService.getBookByName(targetName);
        Assertions.assertNotNull(filteredBooks);
        Assertions.assertFalse(filteredBooks.isEmpty());
        var filteredBook = filteredBooks.get(0);
        Assertions.assertEquals(filteredBook.getName(), targetName);

        var reviewDto = new ReviewDto(Rating.FIVE);
        var oldRatingAvg = filteredBook.getRatingAvg();
        var oldRatingCount = filteredBook.getRatingCount();
        reviewService.createReview(filteredBook.getId(), "user1", reviewDto);
        var updatedBook = realBookService.getBook(filteredBook.getId());
        System.out.println(updatedBook.getRatingCount());
        System.out.println(oldRatingCount);
        Assertions.assertTrue(updatedBook.getRatingAvg().compareTo(oldRatingAvg) > 0);
        Assertions.assertEquals(updatedBook.getRatingCount(), oldRatingCount + 1);

        BookDto bookDto = createOfferedBook();
        offeredBookService.offerBook("user1", bookDto);
        var user1OfferedBook = offeredBookService.getOfferedBookByPerson("user1").get(0);
        Assertions.assertNotNull(user1OfferedBook);



        var user2OfferedBooks = offeredBookService.getOfferedBookByPerson("user2");
        Assertions.assertNull(user2OfferedBooks);



        var allOfferedBooks = offeredBookService.getAllOfferedBooks();
        Assertions.assertNotNull(allOfferedBooks);
        Assertions.assertFalse(allOfferedBooks.isEmpty());
        user1OfferedBook = allOfferedBooks.get(0);
        Assertions.assertEquals(user1OfferedBook.getPersonName(), "user1");

        var feedback = "test_feedback";
        offeredBookService.createFeedback(user1OfferedBook.getId(), feedback);



        var user1OfferedBooks = offeredBookService.getOfferedBookByPerson("user1");
        Assertions.assertNotNull(user1OfferedBooks);
        Assertions.assertFalse(user1OfferedBooks.isEmpty());

        var user1OfferedBookWithFeedback = user1OfferedBooks.get(0);
        Assertions.assertNotNull(user1OfferedBookWithFeedback.getFeedback());
        Assertions.assertEquals(user1OfferedBookWithFeedback.getFeedback(), "test_feedback");

        user1OfferedBookWithFeedback.setTitle("test_new_title");
        offeredBookService.updateOfferedBook(user1OfferedBookWithFeedback.getId(), mapper.toDto(user1OfferedBookWithFeedback));




        allOfferedBooks = offeredBookService.getAllOfferedBooks();
        Assertions.assertNotNull(allOfferedBooks);
        Assertions.assertFalse(allOfferedBooks.isEmpty());

        var user1UpdatedOfferedBook = allOfferedBooks.get(0);
        Assertions.assertEquals(user1UpdatedOfferedBook.getPersonName(), "user1");
        Assertions.assertNull(user1UpdatedOfferedBook.getFeedback());
        Assertions.assertEquals(user1UpdatedOfferedBook.getTitle(), "test_new_title");

        var realBookId = offeredBookService.confirmOffer(user1UpdatedOfferedBook.getId()).getId();

        var user1AllOfferedBooks = offeredBookService.getOfferedBookByPerson("user1");
        Assertions.assertTrue(user1AllOfferedBooks.isEmpty());

        var realBook = realBookService.getBook(realBookId);
        Assertions.assertNotNull(realBook);
        Assertions.assertTrue(isEqualBooks(realBook, user1UpdatedOfferedBook));
    }

    private RealBook createTestBook() {
        RealBook realBook = new RealBook();
        realBook.setIsbn("test");
        realBook.setName("test");
        realBook.setIsbn13("test");
        realBook.setImageUrl("test");
        realBook.setLangCode("test");
        realBook.setOriginalPublicationYear("test");
        realBook.setOriginalTitle("test");
        realBook.setSmallImageUrl("test");
        realBook.setTitle("test");
        realBook.setRatingCount(0L);
        realBook.setRatingAvg(BigDecimal.ZERO);
        return realBook;
    }

    private boolean isEqualBooks(Book book1, Book book2) {
        if (!book1.getImageUrl().equals(book2.getImageUrl())) {
            return false;
        }
        if (!book1.getIsbn().equals(book2.getIsbn())) {
            return false;
        }
        if (!book1.getIsbn13().equals(book2.getIsbn13())) {
            return false;
        }
        if (!book1.getName().equals(book2.getName())) {
            return false;
        }
        if (!book1.getLangCode().equals(book2.getLangCode())) {
            return false;
        }
        if (!book1.getOriginalPublicationYear().equals(book2.getOriginalPublicationYear())) {
            return false;
        }
        if (!book1.getOriginalTitle().equals(book2.getOriginalTitle())) {
            return false;
        }
        if (!book1.getSmallImageUrl().equals(book2.getSmallImageUrl())) {
            return false;
        }
        if (!book1.getTitle().equals(book2.getTitle())) {
            return false;
        }

        return true;
    }

    private BookDto createOfferedBook() {
        BookDto offeredBook = new BookDto();
        offeredBook.setIsbn("test_isbn");
        offeredBook.setName("test_name");
        offeredBook.setIsbn13("test_isbn13");
        offeredBook.setImageUrl("test_imageUrl");
        offeredBook.setLangCode("test_langCode");
        offeredBook.setOriginalPublicationYear("test_year");
        offeredBook.setOriginalTitle("test_orTitle");
        offeredBook.setSmallImageUrl("test_smallImageUrl");
        offeredBook.setTitle("test_title");
        return offeredBook;
    }

}
