package com.liga.libraryapi.service;

public interface Mapper<T, E> {
    E toEntity(T dto);

    E toEntity(E entity, T dto);

    T toDto(E entity);
}
