package com.liga.libraryapi.service.impl;

import com.liga.libraryapi.security.JwtTokenUtils;
import com.liga.libraryapi.security.JwtUserDetailsService;
import com.liga.libraryapi.service.AuthService;
import com.liga.libraryapi.web.dto.AuthRequestDto;
import com.liga.libraryapi.web.dto.AuthResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtils jwtTokenUtils;
    private final JwtUserDetailsService jwtUserDetailsService;

    @Override
    public AuthResponseDto login(AuthRequestDto authRequestDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getLogin(), authRequestDto.getPassword()));
        } catch (BadCredentialsException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(authRequestDto.getLogin());
        String token = jwtTokenUtils.generateToken(userDetails);
        return new AuthResponseDto(token);
    }
}
