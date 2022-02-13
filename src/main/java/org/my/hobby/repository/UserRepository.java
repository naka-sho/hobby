package org.my.hobby.repository;

public interface UserRepository {
    String address();
    void deleteByAddress(String address);
}
