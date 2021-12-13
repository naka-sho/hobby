package org.my.hobby.service;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.my.hobby.core.Book;
import org.my.hobby.repository.BookRepository;

@Singleton
public class BookServiceImpl implements BookService {

    @Inject
    BookRepository bookRepository;

    @Override
    public Book find(String title) {
        return bookRepository.find(title);
    }
}
