CREATE TABLE IF NOT EXISTS person (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS real_book (
    id SERIAL PRIMARY KEY,
    image_url VARCHAR(255),
    isbn VARCHAR(255),
    isbn13 VARCHAR(255),
    name VARCHAR(255),
    original_publication_year VARCHAR(50),
    original_title VARCHAR(255),
    small_image_url VARCHAR(255),
    title VARCHAR(255),
    lang_code VARCHAR(50),
    version INTEGER,
    rating_count BIGINT,
    rating_avg NUMERIC,
    old_book_id BIGINT
);

CREATE TABLE IF NOT EXISTS offered_book (
    id SERIAL PRIMARY KEY,
    image_url VARCHAR(255),
    isbn VARCHAR(255),
    isbn13 VARCHAR(255),
    name VARCHAR(255),
    original_publication_year VARCHAR(50),
    original_title VARCHAR(255),
    small_image_url VARCHAR(255),
    title VARCHAR(255),
    lang_code VARCHAR(50),
    version INTEGER,
    person_name VARCHAR(255) NOT NULL,
    feedback TEXT
);

CREATE TABLE IF NOT EXISTS review (
    id SERIAL PRIMARY KEY,
    comment TEXT,
    rating INTEGER NOT NULL,
    author VARCHAR(255),
    book_id BIGINT,
    FOREIGN KEY (author_id) REFERENCES person(id),
    FOREIGN KEY (book_id) REFERENCES real_book(id)
);
