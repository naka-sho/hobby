package org.my.hobby.persistence;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BookDto{
    private Integer id;
    private String title;
    private String author;
    private LocalDateTime created;
}
