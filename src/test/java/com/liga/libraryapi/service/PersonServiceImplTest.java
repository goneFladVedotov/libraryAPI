package com.liga.libraryapi.service;

import com.liga.libraryapi.data.entity.Person;
import com.liga.libraryapi.data.entity.Role;
import com.liga.libraryapi.data.repository.PersonRepository;
import com.liga.libraryapi.service.impl.PersonServiceImpl;
import com.liga.libraryapi.web.dto.PersonDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;


public class PersonServiceImplTest {
    @Mock
    private PersonRepository personRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void  setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetByName_Success() {
        String name = "name";
        Person expected = new Person();
        expected.setName(name);

        Mockito.when(personRepository.findByName(name)).thenReturn(Optional.of(expected));

        PersonService personService = new PersonServiceImpl(personRepository, passwordEncoder);

        Person person = personService.getByName(name);

        Assertions.assertNotNull(person);
        Assertions.assertEquals(name, person.getName());

        Mockito.verify(personRepository).findByName(name);
    }

    @Test
    public void testGetByName_NotFound_ShouldThrowException() {
        String name = "not found";

        Mockito.when(personRepository.findByName(name)).thenReturn(Optional.empty());

        PersonService personService = new PersonServiceImpl(personRepository, passwordEncoder);

        IllegalStateException exception = Assertions.assertThrows(
                IllegalStateException.class,
                () -> personService.getByName(name)
        );

        Assertions.assertEquals("person not found", exception.getMessage());
    }

    @Test
    public void testIsExistsForId_True() {
        Long id = 1L;

        Mockito.when(personRepository.existsById(id)).thenReturn(true);

        PersonService personService = new PersonServiceImpl(personRepository, passwordEncoder);

        boolean trueResult = personService.isExists(id);

        Assertions.assertTrue(trueResult);

        Mockito.verify(personRepository).existsById(id);
    }

    @Test
    public void testIsExistsForId_False() {
        Long id = 1L;

        Mockito.when(personRepository.existsById(id)).thenReturn(false);

        PersonService personService = new PersonServiceImpl(personRepository, passwordEncoder);

        boolean falseResult = personService.isExists(id);

        Assertions.assertFalse(falseResult);

        Mockito.verify(personRepository).existsById(id);
    }

    @Test
    public void testIsExistsForName_True() {
        String name = "name";

        Mockito.when(personRepository.existsByName(name)).thenReturn(true);

        PersonService personService = new PersonServiceImpl(personRepository, passwordEncoder);

        boolean trueResult = personService.isExists(name);

        Assertions.assertTrue(trueResult);

        Mockito.verify(personRepository).existsByName(name);
    }

    @Test
    public void testIsExistsForName_False() {
        String name = "name";

        Mockito.when(personRepository.existsByName(name)).thenReturn(false);

        PersonService personService = new PersonServiceImpl(personRepository, passwordEncoder);

        boolean falseResult = personService.isExists(name);

        Assertions.assertFalse(falseResult);

        Mockito.verify(personRepository).existsByName(name);
    }

    @Test
    public void testCreate_Success() {
        PersonDto dto = new PersonDto();
        dto.setName("test_name");
        dto.setPassword("test_password");
        dto.setPasswordConfirmation("test_password");
        Role role = Role.ROLE_USER;

        Mockito.when(personRepository.findByName(dto.getName())).thenReturn(Optional.empty());
        Mockito.when(passwordEncoder.encode(dto.getPassword())).thenReturn("encodedPassword");
        Mockito.when(personRepository.save(Mockito.any(Person.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PersonService personService = new PersonServiceImpl(personRepository, passwordEncoder);

        Person person = personService.create(dto, role);

        Assertions.assertNotNull(person);
        Assertions.assertEquals(dto.getName(), person.getName());
        Assertions.assertEquals("encodedPassword", person.getPassword());
        Assertions.assertEquals(role, person.getRole());

        Mockito.verify(personRepository).findByName(dto.getName());
        Mockito.verify(passwordEncoder).encode(dto.getPassword());
        Mockito.verify(personRepository).save(Mockito.any(Person.class));
    }

    @Test
    public void testCreate_PasswordsNotMatching_ShouldThrowException() {
        PersonDto dto = new PersonDto();
        dto.setName("John");
        dto.setPassword("password");
        dto.setPasswordConfirmation("differentPassword");
        Role role = Role.ROLE_USER;

        PersonService personService = new PersonServiceImpl(personRepository, passwordEncoder);

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            personService.create(dto, role);
        });

        Assertions.assertEquals("password and password confirmation are not equals", exception.getMessage());
    }

    @Test
    public void testCreate_PersonAlreadyExists_ShouldThrowException() {
        PersonDto dto = new PersonDto();
        dto.setName("John");
        dto.setPassword("password");
        dto.setPasswordConfirmation("password");
        Role role = Role.ROLE_USER;

        Mockito.when(personRepository.findByName(dto.getName())).thenReturn(Optional.of(new Person()));

        PersonService personService = new PersonServiceImpl(personRepository, passwordEncoder);

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            personService.create(dto, role);
        });

        Assertions.assertEquals("person already exists", exception.getMessage());
    }


}
