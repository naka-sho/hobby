package org.my.hobby.persistence;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)

public class SendLogRecord {
    private Long queueId;
    private String address;
    private String transaction;
    private Long price;
    private String url;
    private LocalDateTime createdAt;
}
