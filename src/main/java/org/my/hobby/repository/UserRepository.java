package org.my.hobby.repository;

import java.util.List;

import org.my.hobby.core.User;

public interface UserRepository {
    String address();
    void deleteByAddress(String address);
    List<User> userSendList();
}
