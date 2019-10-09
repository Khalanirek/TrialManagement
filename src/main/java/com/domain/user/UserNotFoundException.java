package com.domain.user;

import com.domain.error.ObjectNotFoundException;

public class UserNotFoundException extends ObjectNotFoundException {
    public UserNotFoundException(Long userId) {
        super(String.format("User with id %d not found", userId));
    }
}
