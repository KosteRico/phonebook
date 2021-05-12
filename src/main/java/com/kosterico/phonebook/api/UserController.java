package com.kosterico.phonebook.api;


import com.kosterico.phonebook.api.exceptions.UserNotFoundException;
import com.kosterico.phonebook.entities.PhonebookEntry;
import com.kosterico.phonebook.entities.User;
import com.kosterico.phonebook.repositories.PhonebookRepository;
import com.kosterico.phonebook.repositories.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Api(value = "User")
public class UserController {

    private final UserRepository userRepository;

    private final PhonebookRepository phonebookRepository;

    public UserController(UserRepository userRepository, PhonebookRepository phonebookRepository) {
        this.userRepository = userRepository;
        this.phonebookRepository = phonebookRepository;
    }

    @ApiOperation(value = "Search users")
    @GetMapping
    List<User> getUsers(@ApiParam(value = "Search query") @RequestParam(defaultValue = "") String q) {
        return userRepository.search(q);
    }

    @ApiOperation(value = "Create user")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @ApiOperation(value = "Modify user")
    @PatchMapping("/{id}")
    User editUser(@RequestBody User user, @PathVariable Long id) {
        user.setId(id);
        if (userRepository.existsById(id)) {
            return userRepository.save(user);
        } else {
            throw new UserNotFoundException(id);
        }
    }

    @ApiOperation(value = "View user by Id")
    @GetMapping("/{id}")
    User getUser(@PathVariable Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @ApiOperation(value = "Delete user")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUser(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException(id);
        }
    }

    @ApiOperation(value = "View phonebook entries for a user")
    @GetMapping("/{id}/phonebook")
    List<PhonebookEntry> getUserPhonebookEntries(@PathVariable Long id) {
        return phonebookRepository.findByUserId(id);
    }

}
