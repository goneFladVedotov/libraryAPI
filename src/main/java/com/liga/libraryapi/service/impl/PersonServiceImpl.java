package com.liga.libraryapi.service.impl;

import com.liga.libraryapi.data.entity.Person;
import com.liga.libraryapi.data.entity.Role;
import com.liga.libraryapi.data.repository.PersonRepository;
import com.liga.libraryapi.service.PersonService;
import com.liga.libraryapi.web.dto.PersonDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Person create(PersonDto dto, Role role) {
        if (!dto.getPassword().equals(dto.getPasswordConfirmation())) {
            throw new IllegalArgumentException("password and password confirmation are not equals");
        }
        if (personRepository.findByName(dto.getName()).isPresent()) {
            throw new IllegalArgumentException("person already exists");
        }
        Person person = new Person();
        person.setName(dto.getName());
        person.setPassword(passwordEncoder.encode(dto.getPassword()));
        person.setRole(role);
        return personRepository.save(person);
    }

    @Override
    public Person getByName(String name) {
        return personRepository.findByName(name).orElseThrow(() -> new IllegalStateException("person not found"));
    }

    @Override
    public boolean isExists(Long personId) {
        return personRepository.existsById(personId);
    }

    @Override
    public boolean isExists(String personName) {
        return personRepository.existsByName(personName);
    }
}
