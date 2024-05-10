package com.liga.libraryapi.data.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "real_book")
@DiscriminatorValue("REAL")
@Getter
@Setter
public class RealBook extends Book {
    private Long ratingCount;
    private BigDecimal ratingAvg;
    private Long oldBookId;
}
