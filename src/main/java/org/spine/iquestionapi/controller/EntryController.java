package org.spine.iquestionapi.controller;

import org.spine.iquestionapi.model.Entry;
import org.spine.iquestionapi.repository.EntryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/entry")
@ResponseStatus(HttpStatus.OK)
public class EntryController {
    @Autowired private EntryRepo entryRepo;

    // Get entry by id
    @GetMapping("/{id}")
    @ResponseBody
    public Entry getEntryById(@PathVariable(value="id") long id){
        return entryRepo.findById(id).get();
    }

    // Delete an entry
    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteEntry(@PathVariable(value="id") long id){
        entryRepo.deleteById(id);
    }

    // Create an entry
    @PutMapping("/")
    @ResponseBody
    public Entry createEntry(@RequestBody Entry entry){
        return entryRepo.save(entry);
    }
}
