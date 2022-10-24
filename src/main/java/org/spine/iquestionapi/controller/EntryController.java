package org.spine.iquestionapi.controller;

import java.util.List;

import org.spine.iquestionapi.model.User;
import org.spine.iquestionapi.model.Entry;
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

    // Get entry by id
    @GetMapping("/{id}")
    @ResponseBody
    public Entry getEntryById(@PathVariable(value="id") long id){
        List<Entry> entries = authorizationService.getLoggedInUser().getEntries();

        for (Entry entry : entries) {
            if (entry.getId() == id) {
                return entry;
            }
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to view this entry.");
    }

    // Delete an entry
    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteEntry(@PathVariable(value="id") long id){
        List<Entry> entries = authorizationService.getLoggedInUser().getEntries();

        for (Entry entry : entries) {
            if (entry.getId() == id) {
                entryRepo.deleteById(id);
                return;
            }
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to view this entry.");
    }

    // Create an entry
    @PutMapping("/")
    @ResponseBody
    public Entry createEntry(@RequestBody Entry entry){
        // Check if user is a caregiver
        if (authorizationService.getLoggedInUser().getRole() != User.Role.CAREGIVER) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not a caregiver.");
        }

        return entryRepo.save(entry);
    }
}
