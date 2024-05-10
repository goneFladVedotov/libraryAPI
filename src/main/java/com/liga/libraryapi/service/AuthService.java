package com.liga.libraryapi.service;

import com.liga.libraryapi.web.dto.AuthRequestDto;
import com.liga.libraryapi.web.dto.AuthResponseDto;

public interface AuthService {
    AuthResponseDto login(AuthRequestDto authRequestDto);
}
