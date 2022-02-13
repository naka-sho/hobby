package org.my.hobby.service;

import javax.inject.Inject;
import javax.inject.Singleton;

import java.util.List;

import org.my.hobby.core.User;
import org.my.hobby.repository.UserRepository;

@Singleton
public class UserServiceImpl implements UserService {

    @Inject
    UserRepository userRepository;

    @Override
    public void addList(List<String> userList) {
        userRepository.addList(userList);
    }

    @Override
    public void deleteList(List<String> userList) {

    }

    @Override
    public void delete(String address) {
        userRepository.deleteByAddress(address);
    }

    @Override
    public List<User> userSendList() {
        return userRepository.userSendList();
    }
}
