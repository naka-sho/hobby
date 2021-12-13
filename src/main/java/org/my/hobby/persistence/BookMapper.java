package org.my.hobby.persistence;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BookMapper {
    @Select("""
        SELECT
            id,
            title,
            author,
            created
        FROM
            book
        WHERE
            title = #{title}
    """)
    Optional<BookDto> getBook(String title);
}
