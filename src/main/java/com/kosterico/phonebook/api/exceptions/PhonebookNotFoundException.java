package com.kosterico.phonebook.api.exceptions;

public class PhonebookNotFoundException extends NotFoundException {

    public PhonebookNotFoundException(Long id) {
        super("Phonebook not found: id=" + id);
    }
}
