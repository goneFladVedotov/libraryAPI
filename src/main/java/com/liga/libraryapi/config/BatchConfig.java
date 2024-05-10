package com.liga.libraryapi.config;

import com.liga.libraryapi.data.entity.RealBook;
import com.liga.libraryapi.data.entity.Review;
import com.liga.libraryapi.data.repository.RealBookRepository;
import com.liga.libraryapi.data.repository.ReviewRepository;
import com.liga.libraryapi.service.RealBookService;
import com.liga.libraryapi.web.dto.MigratedRealBook;
import com.liga.libraryapi.web.dto.MigratedReview;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {
    private final RealBookRepository realBookRepository;
    private final RealBookService realBookService;
    private final ReviewRepository reviewRepository;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public FlatFileItemReader<MigratedRealBook> bookItemReader() {
        FlatFileItemReader<MigratedRealBook> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("work_book_denormalized.csv"));
        itemReader.setName("csvBookReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(bookLineMapper());
        return itemReader;
    }

    @Bean
    public FlatFileItemReader<MigratedReview> reviewItemReader() {
        FlatFileItemReader<MigratedReview> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("ratings.csv"));
        itemReader.setName("csvReviewReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(reviewLineMapper());
        return itemReader;
    }


    @Bean
    public MigrationRealBookProcessor migrationRealBookProcessor() {
        return new MigrationRealBookProcessor();
    }

    @Bean
    public MigrationReviewProcessor migrationReviewProcessor() {
        return new MigrationReviewProcessor(realBookService);
    }

    @Bean
    public RepositoryItemWriter<RealBook> bookItemWriter() {
        RepositoryItemWriter<RealBook> itemWriter = new RepositoryItemWriter<>();
        itemWriter.setRepository(realBookRepository);
        itemWriter.setMethodName("save");
        return itemWriter;
    }

    @Bean
    public RepositoryItemWriter<Review> reviewItemWriter() {
        RepositoryItemWriter<Review> itemWriter = new RepositoryItemWriter<>();
        itemWriter.setRepository(reviewRepository);
        itemWriter.setMethodName("save");
        return itemWriter;
    }

    @Bean
    public Step bookStep() {
        return new StepBuilder("bookFromCsvToDB", jobRepository)
                .<MigratedRealBook, RealBook>chunk(10, transactionManager)
                .reader(bookItemReader())
                .processor(migrationRealBookProcessor())
                .writer(bookItemWriter())
                .build();
    }

    @Bean
    public Step reviewStep() {
        return new StepBuilder("reviewFromCsvToDB", jobRepository)
                .<MigratedReview, Review>chunk(10, transactionManager)
                .reader(reviewItemReader())
                .processor(migrationReviewProcessor())
                .writer(reviewItemWriter())
                .build();
    }

    @Bean
    public Job job() {
        return new JobBuilder("importFromCsvToDB", jobRepository)
                .start(bookStep())
                .next(reviewStep())
                .build();

    }


    private LineMapper<MigratedRealBook> bookLineMapper() {
        DefaultLineMapper<MigratedRealBook> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id", "book_id", "best_book_id", "work_id", "books_count", "isbn",
                "isbn13", "authors", "original_publication_year", "original_title", "title",
                "language_code", "average_rating", "ratings_count", "work_ratings_count",
                "work_text_reviews_count", "ratings_1", "ratings_2", "ratings_3", "ratings_4",
                "ratings_5", "image_url", "small_image_url");
        BeanWrapperFieldSetMapper<MigratedRealBook> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(MigratedRealBook.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

    private LineMapper<MigratedReview> reviewLineMapper() {
        DefaultLineMapper<MigratedReview> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("book_id", "user_id", "rating");
        BeanWrapperFieldSetMapper<MigratedReview> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(MigratedReview.class);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        lineMapper.setLineTokenizer(lineTokenizer);
        return lineMapper;
    }
}
