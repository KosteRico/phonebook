package com.kosterico.phonebook.repositories;

import com.kosterico.phonebook.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u from User u where upper(u.firstName) " +
            "like upper(concat('%', ?1, '%')) or " +
            "upper(u.lastName) like upper(concat('%', ?1, '%'))")
    List<User> search(String query);
}
