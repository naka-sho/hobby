package org.my.hobby.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.my.hobby.core.User;
import org.my.hobby.persistence.UserListRecord;
import org.my.hobby.persistence.UserSendListRecord;
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

    @Override
    public List<User> userSendList() {
        List<UserSendListRecord> userSendList = usersMapper.userSendList();
        return userSendList.stream().map(e ->
                new User(e.getAddress(), !Objects.isNull(e.getSendLogId()))
        ).collect(Collectors.toList());
    }

    @Override
    public void deleteByAddress(String address) {
        usersMapper.deleteByAddress(address);
    }
}
