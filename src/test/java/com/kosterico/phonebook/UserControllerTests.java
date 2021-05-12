package com.kosterico.phonebook;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kosterico.phonebook.entities.PhonebookEntry;
import com.kosterico.phonebook.entities.User;
import com.kosterico.phonebook.repositories.PhonebookRepository;
import com.kosterico.phonebook.repositories.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    private static User user;

    @MockBean
    private UserRepository repository;

    @MockBean
    private PhonebookRepository phonebookRepository;

    @BeforeAll
    private static void init() {
        user = new User("Jack", "London");
        user.setId(1L);
    }

    @Test
    void post_Created() throws Exception {
        String userStr = mapper.writeValueAsString(user);

        when(repository.save(user)).thenReturn(user);

        this.mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userStr))
                .andExpect(status().isCreated())
                .andExpect(content().json(userStr));
    }

    @Test
    void get_Ok() throws Exception {
        String userStr = mapper.writeValueAsString(user);

        when(repository.findById(user.getId())).thenReturn(Optional.of(user));

        this.mvc.perform(get("/users/{id}", user.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(userStr));
    }

    @Test
    void get_NotFound() throws Exception {
        when(repository.findById(user.getId())).thenReturn(Optional.empty());

        this.mvc.perform(get("/users/{id}", user.getId()))
                .andExpect(status().isNotFound());
    }


    @Test
    void patch_Ok() throws Exception {
        User modified = new User("John", "Doe");
        modified.setId(10L);

        when(repository.existsById(modified.getId())).thenReturn(true);
        when(repository.save(modified)).thenReturn(modified);

        String userStr = mapper.writeValueAsString(modified);

        this.mvc.perform(patch("/users/{id}", modified.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(userStr)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(userStr));
    }

    @Test
    void patch_NotFound() throws Exception {
        String userStr = mapper.writeValueAsString(user);

        when(repository.existsById(user.getId())).thenReturn(false);

        this.mvc.perform(patch("/users/{id}", 99)
                .contentType(MediaType.APPLICATION_JSON)
                .content(userStr)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAll_Ok() throws Exception {
        List<User> users = List.of(user);

        when(repository.search("")).thenReturn(users);

        String usersStr = mapper.writeValueAsString(users);

        this.mvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().json(usersStr));
    }

    @Test
    void delete_Ok() throws Exception {
        when(repository.existsById(user.getId())).thenReturn(true);

        this.mvc.perform(delete("/users/{id}", user.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_NotFound() throws Exception {
        when(repository.existsById(user.getId())).thenReturn(false);

        this.mvc.perform(delete("/users/{id}", user.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void findByUserId_Ok() throws Exception {

        List<PhonebookEntry> phonebookEntries = new LinkedList<>();
        phonebookEntries.add(new PhonebookEntry(1L, "89819507752", 1L));
        phonebookEntries.add(new PhonebookEntry(2L, "89198197709", 2L));
        phonebookEntries.add(new PhonebookEntry(3L, "89272633525", 2L));
        phonebookEntries.add(new PhonebookEntry(4L, "89195541560", 3L));

        List<PhonebookEntry> res = List.of(phonebookEntries.get(0));

        when(phonebookRepository.findByUserId(user.getId())).thenReturn(res);

        String phonebookEntriesStr = mapper.writeValueAsString(res);

        this.mvc.perform(get("/users/{id}/phonebook", user.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(phonebookEntriesStr));
    }


}
