package com.liga.libraryapi.data.repository;

import com.liga.libraryapi.data.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByBookId(Long bookId);

    @Query(value = "SELECT * FROM review WHERE author_id = :authorId", nativeQuery = true)
    Optional<Review> findByAuthorId(@Param("authorId") Long authorId);

    @Query(value = "SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM review r WHERE r.author_id = :authorId AND r.book_id = :bookId", nativeQuery = true)
    boolean existsByAuthorIdAndBookId(@Param("authorId") Long authorId, @Param("bookId") Long bookId);
    void deleteById(Long id);
}
