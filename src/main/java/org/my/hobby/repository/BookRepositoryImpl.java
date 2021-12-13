package org.my.hobby.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import java.util.Optional;

import org.my.hobby.core.Book;
import org.my.hobby.persistence.BookDto;
import org.my.hobby.persistence.BookMapper;

@Singleton
public class BookRepositoryImpl implements BookRepository {

    @Inject
    BookMapper bookMapper;

    @Override
    public Book find(String title) {
        final Optional<BookDto> book = bookMapper.getBook(title);
        return book
                .map(bookDto -> new Book(bookDto.getTitle(), bookDto.getAuthor()))
                .orElse(new Book("", ""));
    }
}
