package org.my.hobby.persistence;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AddressListRecord {
    private String address;
    private Long cnt;
}
