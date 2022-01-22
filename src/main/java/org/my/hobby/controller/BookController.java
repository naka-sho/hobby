package org.my.hobby.controller;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.util.Set;

import org.my.hobby.controller.exception.handler.BadRequestException;
import org.my.hobby.core.Book;
import org.my.hobby.service.BookService;

record BookSearchRequest(
        @NotBlank(message = "Title may not be blank") String title) {
}

record BookSearchResponse(
        boolean success,
        String message,
        Book book) {
    record Book(String title, String author) {
    }
}

@Path("/book/search")
public class BookController {

    @Inject
    Validator validator;

    @Inject
    BookService bookService;

    /**
     * リクエストサンプル
     *
     * curl --location --request POST 'http://localhost:8080/book/search' --header 'Content-Type: application/json' --data-raw '{"title": "hobby"}'                                                                                                         [/Users/shogo.nakao]
     * {"success":true,"message":"","book":{"title":"hobby","author":"s-nakao"}}%
     * curl --location --request POST 'http://localhost:8080/book/search' --header 'Content-Type: application/json' --data-raw '{"title": "s-nakao"}'                                                                                                       [/Users/shogo.nakao]
     * {"success":false,"message":"not found book","book":{"title":"","author":""}}%
     *
     * @param bookSearchRequest
     * @return
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public BookSearchResponse search(BookSearchRequest bookSearchRequest) throws BadRequestException {
        Set<ConstraintViolation<BookSearchRequest>> violations = validator.validate(bookSearchRequest);
        if (!violations.isEmpty()) {
            throw new BadRequestException("not found");
        }

        final Book book = bookService.find(bookSearchRequest.title());
        final BookSearchResponse.Book hobbyBook = new BookSearchResponse.Book(book.title(), book.author());
        return new BookSearchResponse(book.isFind(), book.message(), hobbyBook);
    }
}
