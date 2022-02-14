package org.my.hobby.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.my.hobby.core.User;
import org.my.hobby.persistence.AddressListRecord;
import org.my.hobby.persistence.UserListRecord;
import org.my.hobby.persistence.UserSendListRecord;
import org.my.hobby.persistence.UsersMapper;
import org.my.hobby.persistence.UsersRecord;

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
    public void addList(List<String> userList) {
        List<UsersRecord> collect = userList.stream().map(e ->
                new UsersRecord().setAddress(e)
        ).collect(Collectors.toList());

        usersMapper.insert(collect);
    }

    @Override
    public void deleteList(List<String> userList) {
        List<UsersRecord> collect = userList.stream().map(e ->
                new UsersRecord().setAddress(e)
        ).collect(Collectors.toList());

        usersMapper.delete(collect);
    }

    @Override
    public List<User> userSendList() {
        List<UserSendListRecord> userSendList = usersMapper.userSendList();
        return userSendList.stream().map(e ->
                new User(e.getAddress(), !Objects.isNull(e.getSendLogId()))
        ).collect(Collectors.toList());
    }

    @Override
    public List<User> addressList() {
        List<AddressListRecord> userSendList = usersMapper.addressList();
        return userSendList.stream().map(e ->
                new User(e.getAddress(), e.getCnt() > 0)
        ).collect(Collectors.toList());
    }

    @Override
    public void deleteByAddress(String address) {
        usersMapper.deleteByAddress(address);
    }
}
