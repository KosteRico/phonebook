package com.kosterico.phonebook.repositories;

import com.kosterico.phonebook.entities.PhonebookEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhonebookRepository extends JpaRepository<PhonebookEntry, Long> {
    List<PhonebookEntry> findByUserId(Long userId);

    List<PhonebookEntry> findByPhoneNumberContaining(String query);
}
