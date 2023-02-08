package com.valloyd.carrental.user;

import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

@Repository("File")
public final class UserFileDas implements UserDao {
    @Override
    public List<User> getUsers() {
        File userFile = new File(
                Objects.requireNonNull(getClass().getClassLoader().getResource("users.csv")).getPath());

        try (
                Scanner scanner = new Scanner(userFile)
        ) {
            String line;
            String[] lines;
            List<User> users = new ArrayList<>();

            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                lines = line.split(", ");

                users.add(new User(UUID.fromString(lines[0]), lines[1], lines[2], lines[3]));
            }
            return users;
        } catch (FileNotFoundException e) {
            System.out.println("🚫 Must be valid file...");
        }
        return null;
    }

    @Override
    public Optional<User> getUserById(String id) {
        return getUsers().stream()
                .filter(user -> user.getUserId().equals(UUID.fromString(id)))
                .findFirst();
    }

    // TODO: 2023-02-03: Make able to return multiple users with same data.
    @Override
    public Optional<User> getUserByName(String name) {
        return getUsers().stream()
                .filter(user -> user.getName().equals(name))
                .findFirst();
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return getUsers().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public Optional<User> getUserByPhoneNumber(String phoneNumber) {
        return getUsers().stream()
                .filter(user -> user.getPhoneNumber().equals(phoneNumber))
                .findFirst();
    }

    @Override
    public void saveFile(List<User> users) {
        File userFile = new File(
                Objects.requireNonNull(getClass().getClassLoader().getResource("users.csv")).getPath());
        try (
                FileWriter fileWriter = new FileWriter(userFile);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)
        ) {

            for (int i = 0; i < users.size(); i++) {
                bufferedWriter.write(users.get(i).getUserId().toString() + ", ");
                bufferedWriter.write(users.get(i).getName() + ", ");
                bufferedWriter.write(users.get(i).getEmail() + ", ");
                bufferedWriter.write(users.get(i).getPhoneNumber());
                if (i < users.size() - 1) {
                    bufferedWriter.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addUser(User user) {
        if (getUsers() != null) {
            List<User> users = getUsers();
            users.add(user);

            saveFile(users);
        }
    }

    @Override
    public void updateUser(User user, User user1) {
        if (getUsers() != null) {
            List<User> users = getUsers();
            users.remove(user1);
            users.add(user);

            saveFile(users);
            System.out.println("User with data: " + user.toString() + " updated.");
        }
    }

    @Override
    public void deleteUser(UUID uuid) {
        if (getUsers() != null) {
            List<User> users = getUsers();

            users.stream()
                    .filter(user -> user.getUserId().equals(uuid))
                    .findFirst()
                    .ifPresent(users::remove);

            saveFile(users);
        }
    }

    @Override
    public boolean existsUserWithId(String userId) {
        if (getUsers() != null) {
            return getUsers().stream()
                    .anyMatch(user -> user.getUserId().equals(UUID.fromString(userId)));
        }
        return false;
    }

    @Override
    public boolean existsUserWithEmail(String email) {
        if (getUsers() != null) {
            return getUsers().stream()
                    .anyMatch(user -> user.getEmail().equals(email));
        }
        return false;
    }

    @Override
    public boolean existsUserWithPhoneNumber(String phoneNumber) {
        if (getUsers() != null) {
            return getUsers().stream()
                    .anyMatch(user -> user.getPhoneNumber().equals(phoneNumber));
        }
        return false;
    }
}
