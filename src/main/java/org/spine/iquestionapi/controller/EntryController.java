package org.spine.iquestionapi.controller;

import org.spine.iquestionapi.model.Entry;
import org.spine.iquestionapi.repository.EntryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/entry")
public class EntryController {
    @Autowired private EntryRepo entryRepo;

    // Get entry by id
    @GetMapping("/{id}")
    public Entry getEntryById(@PathVariable(value="id") long id){
        return entryRepo.findById(id).get();
    }

    // Delete an entry
    @DeleteMapping("/{id}")
    public void deleteEntry(@PathVariable(value="id") long id){
        entryRepo.deleteById(id);
    }

    // Create an entry
    @PutMapping("/")
    public Entry createEntry(@RequestBody Entry entry){
        return entryRepo.save(entry);
    }
}
