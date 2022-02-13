package org.my.hobby.service;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.my.hobby.repository.UserRepository;

@Singleton
public class UserServiceImpl implements UserService {

    @Inject
    UserRepository userRepository;

    @Override
    public void addList() {

    }

    @Override
    public void deleteList() {

    }

    @Override
    public void delete(String address) {
        userRepository.deleteByAddress(address);
    }
}
