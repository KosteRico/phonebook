package com.kosterico.phonebook.api;

import com.kosterico.phonebook.api.exceptions.NotFoundException;
import com.kosterico.phonebook.api.exceptions.PhonebookNotFoundException;
import com.kosterico.phonebook.api.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvise {

    private Map<String, String> buildMessageResponse(String message) {
        Map<String, String> resp = new HashMap<>();
        resp.put("message", message);
        return resp;
    }

    @ExceptionHandler({UserNotFoundException.class, PhonebookNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Map<String, String> notFoundHandler(NotFoundException e) {
        return buildMessageResponse(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    Map<String, String> internalErrorHandler(Exception e) {
        return buildMessageResponse(e.getMessage());
    }
}
