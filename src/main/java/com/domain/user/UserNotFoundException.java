package com.domain.user;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(Long userId) {
        super(String.format("User with id %d not found", userId));
    }
}
