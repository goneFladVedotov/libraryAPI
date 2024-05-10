INSERT INTO person (name, password, role)
VALUES ('user1', '$2a$10$06IrEgHxeqYnpKmmZ04qdOMtbxnizFXdTDV4TRBMEN0HoWuL3aPJK', 'ROLE_USER'),
       ('user2', '$2a$10$ZqqgQJsIDYrEEyiDb1y4DejMMQ6vFAI.FD13IlqhVgZTJ4g5dTYne', 'ROLE_USER'),
       ('admin1', '$2a$10$YYxAYPYX29Qsf9lelRXao.7c.RZ5VAltV9KlUW03mhpKbZXrpnl/C', 'ROLE_ADMIN'),
       ('admin2', '$2a$10$50FCdMHyLPexF0RDVWUAD.1xQPOMA4mh7UkA4h2IY5b1UhZ12SYHK', 'ROLE_ADMIN');

INSERT INTO real_book (image_url, isbn, isbn13, name, original_publication_year, original_title, small_image_url, title, lang_code, version, rating_count, rating_avg)
VALUES ('test', 'test', 'test', 'test', 'test', 'test', 'test', 'test', 'test', 1, 0, 0.0);

