package org.my.hobby.service;

import java.util.List;

import org.my.hobby.core.User;

public interface UserService {
    void addList();
    void deleteList();
    void delete(String address);
    public List<User> userSendList();
}
