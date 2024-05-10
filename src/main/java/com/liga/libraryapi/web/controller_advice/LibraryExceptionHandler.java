package com.liga.libraryapi.web.controller_advice;

import org.springframework.http.ResponseEntity;

public interface LibraryExceptionHandler {
    ResponseEntity<String> handleIllegalArgument(IllegalArgumentException e);

    ResponseEntity<String> handleIllegalState(IllegalStateException e);

    ResponseEntity<String> handleException(Exception e);
}
