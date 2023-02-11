package com.valloyd.carrental.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserDao {
    List<User> getUsers();

    Optional<User> getUserById(String id);

    Optional<User> getUserByName(String name);

    Optional<User> getUserByEmail(String email);

    Optional<User> getUserByPhoneNumber(String phoneNumber);

    void saveFile(List<User> users);

    void addUser(User user);

    void updateUser(User user, User user1);

    void deleteUser(UUID userId);

    boolean existsUserWithId(String userId);

    boolean existsUserWithEmail(String email);

    boolean existsUserWithPhoneNumber(String phoneNumber);
}
