package com.liga.libraryapi.data.entity;

import java.util.List;

public enum Rating {
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE;

    public static Rating getRating(int rating) {
        var ratings = List.of(ONE, TWO, THREE, FOUR, FIVE);
        return ratings.get(rating - 1);
    }
}
