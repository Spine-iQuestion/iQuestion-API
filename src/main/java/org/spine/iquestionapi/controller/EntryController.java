package org.spine.iquestionapi.controller;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Hibernate;
import org.spine.iquestionapi.dto.EntryDto;
import org.spine.iquestionapi.model.Entry;
import org.spine.iquestionapi.model.User;
import org.spine.iquestionapi.repository.EntryRepo;
import org.spine.iquestionapi.service.AuthorizationService;
import org.spine.iquestionapi.util.CsvUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;

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
    private AuthorizationService authorizationService;
    CsvUtil csvUtil = new CsvUtil();
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Get all entries
     *
     * @return a list of all entries
     */
    @GetMapping("/all")
    public Set<EntryDto> getAllEntries() {
        Set<Entry> entries = entryRepo.findByCaregiver(authorizationService.getLoggedInUser())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "NO_ENTRIES_FOUND"));
        Set<EntryDto> entryDtos = new HashSet<EntryDto>();

        for (Entry entry : entries) {
            EntryDto entryDto = new EntryDto();
            entryDto = entryDto.fromEntry(entry);
            entryDtos.add(entryDto);
        }

        return entryDtos;
    }

    /**
     * Get an entry by id
     *
     * @param id the id of the entry
     * @return the entry
     */
    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    @ResponseBody
    public EntryDto getEntryById(@PathVariable(value = "id") UUID id) {
        User loggedInUser = authorizationService.getLoggedInUser();
        Entry entry = entryRepo.findByIdAndCaregiver(id, loggedInUser)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ENTRY_NOT_FOUND"));

        Hibernate.initialize(entry.getAnswers());

        return new EntryDto().fromEntry(entry);
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
        Set<Entry> entries = loggedInUser.getEntries();

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
    @Transactional(readOnly = true)
    @GetMapping(value = "/export/{questionnaireId}/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Resource> exportEntryByIdJson(@PathVariable(value = "questionnaireId") UUID id) {

        Set<Entry> entryList = entryRepo.findByQuestionnaireId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "NO_ENTRIES_FOR_QUESTIONNAIRE"));
        if (entryList.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "NO_ENTRIES_FOR_QUESTIONNAIRE");
        }

        // Set to list
        List<Entry> entryList2 = new ArrayList<Entry>(entryList);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            InputStream inputStream = new ByteArrayInputStream(objectMapper.writeValueAsBytes(entryList2));
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
    @Transactional(readOnly = true)
    @GetMapping(value = "/export/{questionnaireId}/csv", produces = "text/csv")
    @ResponseBody
    public ResponseEntity<Resource> exportEntryByIdCsv(@PathVariable(value = "questionnaireId") UUID id)
            throws FileNotFoundException {

        Set<Entry> entryList = entryRepo.findByQuestionnaireId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "NO_ENTRIES_FOR_QUESTIONNAIRE"));

        if (entryList.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "NO_ENTRIES_FOR_QUESTIONNAIRE");
        }

        String csvString = null;
        try {
            List<Entry> entryArrayList = new ArrayList<Entry>(entryList);
            csvString = csvUtil.entryToCsv(entryArrayList, id);
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
    @Transactional()
    public Map<String, Object> createEntry(@RequestBody Entry entry) {
        User loggedInUser = authorizationService.getLoggedInUser();
        entry.setCaregiver(loggedInUser);
        entry.setTimestamp(System.currentTimeMillis());
        entryRepo.save(entry);
        return Collections.singletonMap("status", "Success");
    }
}
