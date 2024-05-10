package com.liga.libraryapi.web.dto;

import lombok.Data;

@Data
public class PersonDto {
    private String name;
    private String password;
    private String passwordConfirmation;
}
