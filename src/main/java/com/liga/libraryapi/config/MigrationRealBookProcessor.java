package com.liga.libraryapi.config;

import com.liga.libraryapi.data.entity.RealBook;
import com.liga.libraryapi.web.dto.MigratedRealBook;
import org.springframework.batch.item.ItemProcessor;

import java.math.BigDecimal;

public class MigrationRealBookProcessor implements ItemProcessor<MigratedRealBook, RealBook> {
    @Override
    public RealBook process(MigratedRealBook item) throws Exception {
        RealBook realBook = new RealBook();
        realBook.setName(item.getTitle());
        realBook.setTitle(item.getTitle());
        realBook.setOriginalTitle(item.getOriginal_title());
        realBook.setLangCode(item.getLanguage_code());
        realBook.setImageUrl(item.getImage_url());
        realBook.setSmallImageUrl(item.getSmall_image_url());
        realBook.setRatingCount(0L);
        realBook.setRatingAvg(BigDecimal.ZERO);
        realBook.setOriginalPublicationYear(item.getOriginal_publication_year());
        realBook.setIsbn13(item.getIsbn13());
        realBook.setIsbn(item.getIsbn());
        realBook.setOldBookId(item.getBook_id());

        return realBook;
    }
}
