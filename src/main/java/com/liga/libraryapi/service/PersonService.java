package com.liga.libraryapi.service;

import com.liga.libraryapi.data.entity.Person;
import com.liga.libraryapi.data.entity.Role;
import com.liga.libraryapi.web.dto.PersonDto;

public interface PersonService {
    Person create(PersonDto dto, Role role);

    Person getByName(String name);

    boolean isExists(Long personId);
    boolean isExists(String personName);
}
