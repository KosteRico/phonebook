package com.kosterico.phonebook;

import com.kosterico.phonebook.entities.User;
import com.kosterico.phonebook.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository repository;

    @AfterEach
    void clear() {
        repository.deleteAll();
    }

    @Test
    public void search() {
        List<User> users = new LinkedList<>();
        users.add(new User("Jean", "Renau"));
        users.add(new User("Brad", "Pitt"));
        users.add(new User("Hugh", "Jackman"));
        users.add(new User("Michael", "Jackson"));

        String query = "jack";

        users = repository.saveAll(users);

        List<User> verifyRes = users.stream()
                .filter(user -> {
                    String queryLower = query.toLowerCase();

                    String firstNameLower = user.getFirstName().toLowerCase();
                    String lastNameLower = user.getLastName().toLowerCase();

                    return firstNameLower.contains(queryLower) || lastNameLower.contains(queryLower);
                })
                .sorted()
                .collect(Collectors.toList());

        List<User> testRes = repository.search(query).stream().sorted().collect(Collectors.toList());

        Assertions.assertEquals(verifyRes, testRes);
    }

}
