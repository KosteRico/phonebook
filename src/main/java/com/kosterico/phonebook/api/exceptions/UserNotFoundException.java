package com.kosterico.phonebook.api.exceptions;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException(Long id) {
        super("User not found: id=" + id);
    }
}
