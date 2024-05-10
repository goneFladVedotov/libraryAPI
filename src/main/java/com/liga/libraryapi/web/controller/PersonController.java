package com.liga.libraryapi.web.controller;

import com.liga.libraryapi.data.entity.Person;
import com.liga.libraryapi.web.dto.AuthRequestDto;
import com.liga.libraryapi.web.dto.AuthResponseDto;
import com.liga.libraryapi.web.dto.PersonDto;
import org.springframework.http.ResponseEntity;

public interface PersonController {
    ResponseEntity<Person> createUser(PersonDto dto);

    ResponseEntity<Person> createAdmin(PersonDto dto);

    ResponseEntity<AuthResponseDto> authorizePerson(AuthRequestDto authRequestDto);
}
