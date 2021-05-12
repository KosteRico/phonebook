package com.kosterico.phonebook;

import com.kosterico.phonebook.entities.PhonebookEntry;
import com.kosterico.phonebook.entities.User;
import com.kosterico.phonebook.repositories.PhonebookRepository;
import com.kosterico.phonebook.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    //@Bean
    CommandLineRunner initDatabase(UserRepository userRepository, PhonebookRepository phonebookRepository) {
        return args -> {
            System.out.println(userRepository.save(new User("John", "Malkovich")));
            System.out.println(userRepository.save(new User("Jack", "London")));
            System.out.println(phonebookRepository.save(new PhonebookEntry("1234", 1L)));
            System.out.println(phonebookRepository.save(new PhonebookEntry("12345", 2L)));
        };
    }
}
