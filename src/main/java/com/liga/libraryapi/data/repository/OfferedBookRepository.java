package com.liga.libraryapi.data.repository;

import com.liga.libraryapi.data.entity.OfferedBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferedBookRepository extends JpaRepository<OfferedBook, Long> {
    List<OfferedBook> findAllByPersonName(String personName);

    List<OfferedBook> findAllByFeedbackIsNull();

    void deleteById(Long id);
}
