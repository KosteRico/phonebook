package com.kosterico.phonebook;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kosterico.phonebook.entities.PhonebookEntry;
import com.kosterico.phonebook.repositories.PhonebookRepository;
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
public class PhonebookControllerTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    private static List<PhonebookEntry> phonebookEntries;

    private int indexToSave = 1;

    @MockBean
    private PhonebookRepository repository;

    private PhonebookEntry getOneEntry() {
        return phonebookEntries.get(indexToSave);
    }

    @BeforeAll
    private static void init() {
        phonebookEntries = new LinkedList<>();
        phonebookEntries.add(new PhonebookEntry(1L, "89819507752", 1L));
        phonebookEntries.add(new PhonebookEntry(2L, "89198197709", 1L));
        phonebookEntries.add(new PhonebookEntry(3L, "89272633525", 1L));
        phonebookEntries.add(new PhonebookEntry(4L, "89195541560", 1L));
    }

    @Test
    void post_Created() throws Exception {
        String entryStr = mapper.writeValueAsString(getOneEntry());

        when(repository.save(getOneEntry())).thenReturn(getOneEntry());

        this.mvc.perform(post("/phonebook")
                .contentType(MediaType.APPLICATION_JSON)
                .content(entryStr))
                .andExpect(status().isCreated())
                .andExpect(content().json(entryStr));
    }

    @Test
    void get_Ok() throws Exception {
        String entryStr = mapper.writeValueAsString(getOneEntry());

        when(repository.findById(getOneEntry().getId())).thenReturn(Optional.of(getOneEntry()));

        this.mvc.perform(get("/phonebook/{id}", getOneEntry().getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(entryStr));
    }

    @Test
    void get_NotFound() throws Exception {
        when(repository.findById(getOneEntry().getId())).thenReturn(Optional.empty());

        this.mvc.perform(get("/phonebook/{id}", getOneEntry().getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void patch_Ok() throws Exception {

        PhonebookEntry modified = new PhonebookEntry("8119244432", 2L);
        modified.setId(10L);

        when(repository.existsById(modified.getId())).thenReturn(true);

        when(repository.save(modified)).thenReturn(modified);

        String entryStr = mapper.writeValueAsString(modified);

        this.mvc.perform(patch("/phonebook/{id}", modified.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(entryStr)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(entryStr));
    }

    @Test
    void patch_NotFound() throws Exception {
        PhonebookEntry modified = new PhonebookEntry("8119244432", 2L);
        modified.setId(10L);

        when(repository.existsById(modified.getId())).thenReturn(false);

        String entryStr = mapper.writeValueAsString(modified);

        this.mvc.perform(patch("/phonebook/{id}", modified.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(entryStr)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAll_Ok() throws Exception {
        when(repository.findByPhoneNumberContaining("")).thenReturn(phonebookEntries);

        String usersStr = mapper.writeValueAsString(phonebookEntries);

        this.mvc.perform(get("/phonebook"))
                .andExpect(status().isOk())
                .andExpect(content().json(usersStr));
    }

    @Test
    void delete_Ok() throws Exception {
        when(repository.existsById(getOneEntry().getId())).thenReturn(true);

        this.mvc.perform(delete("/phonebook/{id}", getOneEntry().getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_NotFound() throws Exception {
        when(repository.existsById(getOneEntry().getId())).thenReturn(false);

        this.mvc.perform(delete("/phonebook/{id}", getOneEntry().getId()))
                .andExpect(status().isNotFound());
    }


}
