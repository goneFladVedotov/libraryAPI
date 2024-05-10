package com.liga.libraryapi.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "review")
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;
    @Enumerated(value = EnumType.ORDINAL)
    private Rating rating;
    private String author;
    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private RealBook book;
}
