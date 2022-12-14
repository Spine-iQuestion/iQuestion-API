package org.spine.iquestionapi.controller;

import org.assertj.core.util.Files;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.spine.iquestionapi.model.Entry;
import org.spine.iquestionapi.model.LoginCredentials;
import org.spine.iquestionapi.model.User;
import org.spine.iquestionapi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestEntryController {

    @Autowired private EntryController entryController;
    
    @Test
    public void testCorrectEntry() {
        // Arrange
        UUID entryId = UUID.randomUUID();

        // Act
        Entry result = entryController.getEntryById(entryId);

        // Assert
        assertNotNull(result);
    }


    @BeforeEach

    @Test
    @Transactional
    public void testExportFileTypeIsCSV() throws FileNotFoundException {
        //Arrange
        UUID entryId = UUID.randomUUID();
        String expected = "text/csv";

        //Act
        ResponseEntity<Resource> testFile = entryController.exportEntryByIdCsv(entryId);

        //Assert
        assertEquals(expected, testFile.getHeaders().getContentType().toString());

    }

    @Test
    @Transactional
    public void testExportJsonIsObject() throws FileNotFoundException {
        //Arrange
        UUID testId = UUID.randomUUID();
        ArrayList<Entry> expected = new ArrayList<>();

        //Act
        ArrayList<Entry> testArray = entryController.exportEntryByIdJson(testId);

        //Assert
        assertEquals(expected.getClass(), testArray.getClass());

    }
}
