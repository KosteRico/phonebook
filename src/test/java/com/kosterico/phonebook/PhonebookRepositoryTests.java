package com.kosterico.phonebook;

import com.kosterico.phonebook.entities.PhonebookEntry;
import com.kosterico.phonebook.repositories.PhonebookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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
public class PhonebookRepositoryTests {

    @Autowired
    private PhonebookRepository repository;

    private static List<PhonebookEntry> phonebookEntries = new LinkedList<>();

    @BeforeAll
    public static void init() {
        phonebookEntries = new LinkedList<>();
        phonebookEntries.add(new PhonebookEntry("89192121212", 1L));
        phonebookEntries.add(new PhonebookEntry("89192122212", 1L));
        phonebookEntries.add(new PhonebookEntry("83233121212", 1L));
        phonebookEntries.add(new PhonebookEntry("83232121212", 2L));
        phonebookEntries.add(new PhonebookEntry("83233990212", 3L));
    }

    @AfterEach
    void clear() {
        repository.deleteAll();
    }

    @Test
    public void findByUserId() {
        List<PhonebookEntry> phonebookEntriesCreated = repository.saveAll(PhonebookRepositoryTests.phonebookEntries);

        long targetId = 2L;

        List<PhonebookEntry> verifyRes = phonebookEntriesCreated.stream()
                .filter(p -> p.getUserId().equals(targetId))
                .sorted()
                .collect(Collectors.toList());

        List<PhonebookEntry> testRes = repository.findByUserId(targetId).stream().sorted().collect(Collectors.toList());

        Assertions.assertEquals(verifyRes, testRes);
    }

    @Test
    public void findByPhoneNumberContaining() {
        List<PhonebookEntry> phonebookEntriesCreated = repository.saveAll(PhonebookRepositoryTests.phonebookEntries);

        String targetQuery = "981";

        List<PhonebookEntry> verifyRes = phonebookEntriesCreated
                .stream()
                .filter(p -> p.getPhoneNumber().contains(targetQuery))
                .sorted()
                .collect(Collectors.toList());

        List<PhonebookEntry> testRes = repository.findByPhoneNumberContaining(targetQuery)
                .stream()
                .sorted()
                .collect(Collectors.toList());

        Assertions.assertEquals(verifyRes, testRes);
    }


}
