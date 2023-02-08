package com.valloyd.carrental.user;

public record UserDataRequest(
        String name,
        String email,
        String phoneNumber
) {
}
