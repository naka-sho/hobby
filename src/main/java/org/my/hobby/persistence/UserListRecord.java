package org.my.hobby.persistence;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)

public class UserListRecord {
    private Long userId;
    private String address;
    private String twitterAccount;
    private LocalDateTime createdAt;
}
