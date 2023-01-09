package org.spine.iquestionapi.controller;

import org.spine.iquestionapi.model.Entry;
import org.spine.iquestionapi.model.Questionnaire;
import org.spine.iquestionapi.model.User;
import org.spine.iquestionapi.repository.EntryRepo;
import org.spine.iquestionapi.repository.QuestionnaireRepo;
import org.spine.iquestionapi.service.AuthorizationService;
import org.spine.iquestionapi.util.CsvUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The controller for the entry
 */
@RestController
@RequestMapping("/entry")
@ResponseStatus(HttpStatus.OK)
public class EntryController {
    @Autowired
    private EntryRepo entryRepo;
    @Autowired
    private QuestionnaireRepo questionnaireRepo;
    @Autowired
    private AuthorizationService authorizationService;
    CsvUtil csvUtil = new CsvUtil();

    /**
     * Get all entries
     *
     * @return a list of all entries
     */
    @GetMapping("/all")
    public List<Entry> getAllEntries() {
        User loggedInUser = authorizationService.getLoggedInUser();
        List<Entry> entries = loggedInUser.getEntries();
        return entries;
    }

    /**
     * Get an entry by id
     *
     * @param id the id of the entry
     * @return the entry
     */
    @GetMapping("/{id}")
    @ResponseBody
    public Entry getEntryById(@PathVariable(value = "id") UUID id) {
        User loggedInUser = authorizationService.getLoggedInUser();
        List<Entry> entries = loggedInUser.getEntries();

        for (Entry entry : entries) {
            if (entry.getId() == id) {
                return entry;
            }
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ENTRY_NOT_FOUND");
    }

    /**
     * Delete an entry by id
     *
     * @param id the id of the entry
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteEntry(@PathVariable(value = "id") UUID id) {
        User loggedInUser = authorizationService.getLoggedInUser();
        List<Entry> entries = loggedInUser.getEntries();

        for (Entry entry : entries) {
            if (entry.getId() == id) {
                entryRepo.deleteById(id);
                return;
            }
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ENTRY_NOT_FOUND");
    }

    /**
     * Export entries of a questionnaire to a json file
     *
     * @param id the id of the questionnaire
     * @return the json file
     */
    @GetMapping(value = "/export/{questionnaireId}/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Resource> exportEntryByIdJson(@PathVariable(value = "questionnaireId") UUID id) {
        Questionnaire questionnaire = questionnaireRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "QUESTIONNAIRE_NOT_FOUND"));
        ArrayList<Entry> entryList = entryRepo.findByQuestionnaire(questionnaire).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "NO_ENTRIES_FOR_QUESTIONNAIRE"));

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            InputStream inputStream = new ByteArrayInputStream(objectMapper.writeValueAsBytes(entryList));
        InputStreamResource inputStreamResource = new InputStreamResource(inputStream);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(inputStreamResource);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "INTERNAL_SERVER_ERROR");
        }
        
    }

    /**
     * Export entries of a questionnaire to a csv file
     *
     * @param id the id of the questionnaire
     * @return the csv file
     * @throws FileNotFoundException if the file is not found
     */
    @GetMapping(value = "/export/{questionnaireId}/csv", produces = "text/csv")
    @ResponseBody
    public ResponseEntity<Resource> exportEntryByIdCsv(@PathVariable(value = "questionnaireId") UUID id) throws FileNotFoundException {
        Questionnaire questionnaire = questionnaireRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "QUESTIONNAIRE_NOT_FOUND"));
        ArrayList<Entry> entryList = entryRepo.findByQuestionnaire(questionnaire).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "NO_ENTRIES_FOR_QUESTIONNAIRE"));
        if (entryList.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "NO_ENTRIES_FOR_QUESTIONNAIRE");
        }

        String csvString = null;
        try {
            csvString = csvUtil.entryToCsv(entryList, id);
            InputStream targetStream = new ByteArrayInputStream(csvString.getBytes());
            InputStreamResource resource = new InputStreamResource(targetStream);

            return ResponseEntity.ok()
                    .contentLength(csvString.length())
                    .contentType(MediaType.parseMediaType("text/csv"))
                    .body(resource);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR");
        }

    }

    /**
     * Create an entry
     *
     * @param entry the entry to be created
     * @return the created entry
     */
    @PutMapping("/")
    @ResponseBody
    public Entry createEntry(@RequestBody Entry entry) {
        User loggedInUser = authorizationService.getLoggedInUser();
        entry.setCaregiver(loggedInUser);
        entry.setTimestamp(System.currentTimeMillis());
        return entryRepo.save(entry);
    }
}
