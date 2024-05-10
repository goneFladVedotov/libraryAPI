package com.liga.libraryapi.data.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "offered_book")
@DiscriminatorValue("OFFERED")
@Getter
@Setter
public class OfferedBook extends Book {
    private String personName;
    private String feedback;
}
