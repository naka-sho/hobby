package org.my.hobby.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import java.util.Optional;

import org.my.hobby.persistence.UserListRecord;
import org.my.hobby.persistence.UsersMapper;

@Singleton
public class UserRepositoryImpl implements UserRepository {
    @Inject
    UsersMapper usersMapper;

    @Override
    public String address() {
        Optional<UserListRecord> user = usersMapper.user();
        return user.map(
                e -> e.getAddress()
        ).orElse("");
    }
}
