package com.liga.libraryapi.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
public abstract class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String imageUrl;
    private String isbn;
    private String isbn13;
    private String name;
    private String originalPublicationYear;
    private String originalTitle;
    private String smallImageUrl;
    private String title;
    private String langCode;
    @Version
    private Integer version;
}
