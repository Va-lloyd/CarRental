package com.valloyd.carrental.user;

import com.valloyd.carrental.exception.DuplicateResourceException;
import com.valloyd.carrental.exception.RequestValidationException;
import com.valloyd.carrental.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public final class UserService {
    private final UserDao userDao;

    public UserService(@Qualifier("UserFile") UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getUsers() {
        return userDao.getUsers();
    }

    public User getUserById(String id) {
        return userDao.getUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID \"%s\" not found.".formatted(id)));
    }

    public User getUserByName(String name) {
        return userDao.getUserByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("User with name \"%s\" not found.".formatted(name)));
    }

    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email \"%s\" not found.".formatted(email)));
    }

    public User getUserByPhoneNumber(String phoneNumber) {
        return userDao.getUserByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ResourceNotFoundException("User with phone number \"%s\" not found.".formatted(phoneNumber)));
    }

    public void addUser(UserDataRequest request) {
        if (userDao.existsUserWithEmail(request.email())) {
            throw new DuplicateResourceException("Email already taken.");
        } else if (userDao.existsUserWithPhoneNumber(request.phoneNumber())) {
            throw new DuplicateResourceException("Phone number already taken.");
        }

        User user = new User(
                UUID.randomUUID()
                , request.name()
                , request.email()
                , request.phoneNumber());

        userDao.addUser(user);
        System.out.println("User with data: " + user + " added to database.");
    }

    public void updateUser(String userId, UserDataRequest request) {
        User user = getUserById(userId);
        boolean changes = false;

        if (request.name() != null && !request.name().equals(user.getName())) {
            user.setName(request.name());
            changes = true;
        }

        if (request.email() != null && !request.email().equals(user.getEmail())) {
            user.setEmail(request.email());
            changes = true;
        }

        if (request.phoneNumber() != null && !request.phoneNumber().equals(user.getPhoneNumber())) {
            user.setPhoneNumber(request.phoneNumber());
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException("No data changes found.");
        }
        User user1 = getUserById(userId);
        userDao.updateUser(user, user1);
    }

    public void deleteUser(String userId) {
        if (userDao.existsUserWithId(userId)) {
            System.out.println("User with data: " + getUserById(userId).toString() + " deleted from database.");

            userDao.deleteUser(UUID.fromString(userId));
        } else {
            throw new ResourceNotFoundException(
                    "User with ID: %s not found".formatted(userId)
            );
        }
    }
}
