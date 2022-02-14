package org.my.hobby.service;

import java.util.List;

import org.my.hobby.core.User;

public interface UserService {
    void addList(List<String> userList);
    void deleteList(List<String> userList);
    void delete(String address);
    List<User> userSendList();
    List<User> addressList();
}
