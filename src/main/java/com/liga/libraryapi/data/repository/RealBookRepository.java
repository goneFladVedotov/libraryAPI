package com.liga.libraryapi.data.repository;

import com.liga.libraryapi.data.entity.RealBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RealBookRepository extends JpaRepository<RealBook, Long> {
    List<RealBook> findByName(String name);

    List<RealBook> findByIsbn(String isbn);

    Optional<RealBook> findByOldBookId(Long oldBookId);

    boolean existsById(Long id);
}
