package com.kosterico.phonebook.api;

import com.kosterico.phonebook.api.exceptions.PhonebookNotFoundException;
import com.kosterico.phonebook.entities.PhonebookEntry;
import com.kosterico.phonebook.repositories.PhonebookRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/phonebook")
@RestController
public class PhonebookController {

    private final PhonebookRepository repository;

    public PhonebookController(PhonebookRepository repository) {
        this.repository = repository;
    }

    @ApiOperation(value = "Search phonebook entries")
    @GetMapping
    List<PhonebookEntry> getPhonebookEntries(@ApiParam(value = "Search query") @RequestParam(defaultValue = "") String q) {
        return repository.findByPhoneNumberContaining(q);
    }

    @ApiOperation(value = "Create phonebook entry")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    PhonebookEntry createPhonebookEntry(@RequestBody PhonebookEntry phonebookEntry) {
        return repository.save(phonebookEntry);
    }

    @ApiOperation(value = "Edit phonebook entry")
    @PatchMapping("/{id}")
    PhonebookEntry editPhonebookEntry(@RequestBody PhonebookEntry phonebookEntry, @PathVariable Long id) {
        phonebookEntry.setId(id);
        if (repository.existsById(id)) {
            return repository.save(phonebookEntry);
        } else {
            throw new PhonebookNotFoundException(id);
        }
    }

    @ApiOperation(value = "Delete phonebook entry")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deletePhonebookEntry(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new PhonebookNotFoundException(id);
        }
    }

    @ApiOperation(value = "View phonebook entry by Id")
    @GetMapping("/{id}")
    PhonebookEntry getPhonebookEntry(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new PhonebookNotFoundException(id));
    }


}
