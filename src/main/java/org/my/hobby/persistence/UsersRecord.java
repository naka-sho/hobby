package org.my.hobby.persistence;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UsersRecord {
    private Long userId;
    private String address;
    private LocalDateTime createdAt;
}
