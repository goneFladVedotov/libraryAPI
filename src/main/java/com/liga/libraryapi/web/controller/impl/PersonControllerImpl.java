package com.liga.libraryapi.web.controller.impl;

import com.liga.libraryapi.data.entity.Person;
import com.liga.libraryapi.data.entity.Role;
import com.liga.libraryapi.service.AuthService;
import com.liga.libraryapi.service.PersonService;
import com.liga.libraryapi.web.controller.PersonController;
import com.liga.libraryapi.web.dto.AuthRequestDto;
import com.liga.libraryapi.web.dto.AuthResponseDto;
import com.liga.libraryapi.web.dto.PersonDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/library-api/v1/person")
@RequiredArgsConstructor
public class PersonControllerImpl implements PersonController {
    private final AuthService authService;
    private final PersonService personService;

    @PostMapping("/registration/user")
    @Override
    public ResponseEntity<Person> createUser(@RequestBody PersonDto dto) {
        return ResponseEntity.ok(personService.create(dto, Role.ROLE_USER));
    }

    @PostMapping("/registration/admin")
    @Override
    public ResponseEntity<Person> createAdmin(@RequestBody PersonDto dto) {
        return ResponseEntity.ok(personService.create(dto, Role.ROLE_ADMIN));
    }

    @PostMapping("/auth")
    @Override
    public ResponseEntity<AuthResponseDto> authorizePerson(@RequestBody AuthRequestDto authRequestDto) {
        return ResponseEntity.ok(authService.login(authRequestDto));
    }
}
