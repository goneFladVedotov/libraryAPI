package com.liga.libraryapi.config;

import com.liga.libraryapi.data.repository.OfferedBookRepository;
import com.liga.libraryapi.data.repository.PersonRepository;
import com.liga.libraryapi.data.repository.RealBookRepository;
import lombok.RequiredArgsConstructor;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestConfiguration
@RequiredArgsConstructor
public class TestConfig {
    @Bean
    @Primary
    public OfferedBookRepository offeredBookRepository() {
        return Mockito.mock(OfferedBookRepository.class);
    }

    @Bean
    @Primary
    public RealBookRepository realBookRepository() {
        return Mockito.mock(RealBookRepository.class);
    }

    @Bean
    @Primary
    public PersonRepository personRepository() {
        return Mockito.mock(PersonRepository.class);
    }

    @Bean
    @Primary
    public PasswordEncoder passwordEncoder() {
        return Mockito.mock(BCryptPasswordEncoder.class);
    }
}
