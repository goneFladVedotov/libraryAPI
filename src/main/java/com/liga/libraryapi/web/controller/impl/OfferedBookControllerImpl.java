package com.liga.libraryapi.web.controller.impl;

import com.liga.libraryapi.data.entity.OfferedBook;
import com.liga.libraryapi.data.entity.RealBook;
import com.liga.libraryapi.service.OfferedBookService;
import com.liga.libraryapi.web.controller.OfferedBookController;
import com.liga.libraryapi.web.dto.BookDto;
import com.liga.libraryapi.web.dto.FeedbackDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library-api/v1/book/offered")
@RequiredArgsConstructor
public class OfferedBookControllerImpl implements OfferedBookController {
    private final OfferedBookService offeredBookService;

    @PostMapping("/user")
    @Override
    public ResponseEntity<OfferedBook> offerBook(@RequestBody BookDto dto) {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(offeredBookService.offerBook(username, dto));
    }

    @PutMapping("/user/{offeredBookId}")
    @Override
    public ResponseEntity<OfferedBook> updateOfferedBook(@PathVariable Long offeredBookId, @RequestBody BookDto dto) {
        return ResponseEntity.ok(offeredBookService.updateOfferedBook(offeredBookId, dto));
    }

    @GetMapping("/admin")
    @Override
    public ResponseEntity<List<OfferedBook>> getAllOfferedBooks() {
        return ResponseEntity.ok(offeredBookService.getAllOfferedBooks());
    }

    @GetMapping("/user")
    @Override
    public ResponseEntity<List<OfferedBook>> getAllOfferedBooksByPerson() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(offeredBookService.getOfferedBookByPerson(username));
    }

    @PostMapping("/admin/feedback/{offeredBookId}")
    @Override
    public ResponseEntity<OfferedBook> createFeedback(@PathVariable Long offeredBookId, @RequestBody FeedbackDto dto) {
        return ResponseEntity.ok(offeredBookService.createFeedback(offeredBookId, dto.getFeedback()));
    }

    @PostMapping("/admin/confirm/{offeredBookId}")
    @Override
    public ResponseEntity<RealBook> confirmOffer(@PathVariable Long offeredBookId) {
        return ResponseEntity.ok(offeredBookService.confirmOffer(offeredBookId));
    }
}
