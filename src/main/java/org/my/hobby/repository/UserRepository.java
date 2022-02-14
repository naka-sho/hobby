package org.my.hobby.repository;

import java.util.List;

import org.my.hobby.core.User;

public interface UserRepository {
    String address();
    void addList(List<String> userList);
    void deleteList(List<String> userList);
    void deleteByAddress(String address);
    List<User> userSendList();
    List<User> addressList();
}
