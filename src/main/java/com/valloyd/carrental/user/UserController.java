package com.valloyd.carrental.user;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/findId/{userId}")
    public User getUserById(
            @PathVariable("userId") String id
    ) {
        return userService.getUserById(id);
    }

    @GetMapping("/findName/{userName}")
    public User getUserByName(
            @PathVariable("userName") String name
    ) {
        return userService.getUserByName(name);
    }

    @GetMapping("/findEmail/{userEmail}")
    public User getUserByEmail(
            @PathVariable("userEmail") String email
    ) {
        return userService.getUserByEmail(email);
    }

    @GetMapping("/findPhoneNumber/{userPhoneNumber}")
    public User getUserByPhoneNumber(
            @PathVariable("userPhoneNumber") String phoneNumber
    ) {
        return userService.getUserByPhoneNumber(phoneNumber);
    }

    @PostMapping("/addUser")
    public void addUser(
            @RequestBody UserDataRequest request
    ) {
        userService.addUser(request);
    }

    @PutMapping("/updateUser/{userId}")
    public void updateUser(
            @PathVariable("userId") String userId,
            @RequestBody UserDataRequest request
    ) {
        userService.updateUser(userId, request);
    }

    @DeleteMapping("/deleteUser/{userId}")
    public void deleteUser(
            @PathVariable("userId") String userId
    ) {
        userService.deleteUser(userId);
    }
}
