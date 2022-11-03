package org.spine.iquestionapi.controller;

import java.util.List;

import org.spine.iquestionapi.model.Entry;
import org.spine.iquestionapi.model.User;
import org.spine.iquestionapi.repository.EntryRepo;
import org.spine.iquestionapi.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/entry")
@ResponseStatus(HttpStatus.OK)
public class EntryController {
    @Autowired private EntryRepo entryRepo;
    @Autowired private AuthorizationService authorizationService;

    @GetMapping("/all")
    public List<Entry> getAllEntries() {
        User loggedInUser = authorizationService.getLoggedInUser();
        List<Entry> entries = loggedInUser.getEntries();
        return entries;
    }

    // Get entry by id
    @GetMapping("/{id}")
    @ResponseBody
    public Entry getEntryById(@PathVariable(value="id") long id){
        User loggedInUser = authorizationService.getLoggedInUser();
        List<Entry> entries = loggedInUser.getEntries();

        for (Entry entry : entries) {
            if (entry.getId() == id) {
                return entry;
            }
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The entry was not found.");
    }

    // Delete an entry
    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteEntry(@PathVariable(value="id") long id){
        User loggedInUser = authorizationService.getLoggedInUser();
        List<Entry> entries = loggedInUser.getEntries();

        for (Entry entry : entries) {
            if (entry.getId() == id) {
                entryRepo.deleteById(id);
                return;
            }
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The entry was not found.");
    }

    // Create an entry
    @PutMapping("/")
    @ResponseBody
    public Entry createEntry(@RequestBody Entry entry){
        User loggedInUser = authorizationService.getLoggedInUser();
        entry.setCaregiver(loggedInUser);
        entry.setTimestamp(System.currentTimeMillis());
        return entryRepo.save(entry);
    }
}
