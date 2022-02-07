package org.my.hobby.persistence;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserTwitterRecord {
    private Long userId;
    private String twitterId;
    private LocalDateTime createdAt;
}
