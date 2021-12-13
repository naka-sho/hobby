package org.my.hobby.repository;

import org.my.hobby.core.Book;

public interface BookRepository {
    Book find(String title);
}