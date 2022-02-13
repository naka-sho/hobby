package org.my.hobby.core;

import java.security.PrivateKey;

public record User(
        String address,
        boolean send
) {
}
