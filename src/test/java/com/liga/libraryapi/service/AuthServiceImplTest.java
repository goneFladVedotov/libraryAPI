package com.liga.libraryapi.service;

import com.liga.libraryapi.security.JwtTokenUtils;
import com.liga.libraryapi.security.JwtUserDetailsService;
import com.liga.libraryapi.service.impl.AuthServiceImpl;
import com.liga.libraryapi.web.dto.AuthRequestDto;
import com.liga.libraryapi.web.dto.AuthResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

public class AuthServiceImplTest {
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtTokenUtils jwtTokenUtils;
    @Mock
    private JwtUserDetailsService jwtUserDetailsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogin_Success() {
        String token = "token";
        AuthRequestDto requestDto = new AuthRequestDto();
        requestDto.setLogin("login");
        requestDto.setPassword("password");
        UserDetails userDetails = new User("login", "password", Collections.emptyList());

        Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);
        Mockito.when(jwtUserDetailsService.loadUserByUsername("login"))
                .thenReturn(userDetails);
        Mockito.when(jwtTokenUtils.generateToken(Mockito.any(UserDetails.class)))
                .thenReturn(token);

        AuthService authService = new AuthServiceImpl(authenticationManager, jwtTokenUtils, jwtUserDetailsService);

        AuthResponseDto result = authService.login(requestDto);

        Assertions.assertNotNull(result);

        Assertions.assertEquals(token, result.getToken());

        Mockito.verify(authenticationManager).authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));
        Mockito.verify(jwtUserDetailsService).loadUserByUsername("login");
        Mockito.verify(jwtTokenUtils).generateToken(Mockito.any(UserDetails.class));
    }

    @Test
    public void testLogin_BadCredentials_ShouldThrowException() {
        AuthRequestDto requestDto = new AuthRequestDto();
        requestDto.setLogin("login");
        requestDto.setPassword("password");
        String message = "message";

        Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(BadCredentialsException.class);

        AuthService authService = new AuthServiceImpl(authenticationManager, jwtTokenUtils, jwtUserDetailsService);

        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> authService.login(requestDto)
        );

        Mockito.verify(authenticationManager).authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));
    }
}
