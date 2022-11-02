package org.spine.iquestionapi.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import org.spine.iquestionapi.model.Entry;
import org.spine.iquestionapi.model.LoginCredentials;
import java.util.Map;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestEntryController {
    @Autowired
    private EntryController entryController;

    @Test
    public void testCorrectEntry() {
        // Arrange
        long id = 234;

        // Act
        Entry result = entryController.getEntryById(id);

        // Assert
        assertNotNull(result);
    }

    @Test(expected = ResponseStatusException.class)
    public void testIncorrectEntry() {
        // Arrange
        long id = -1;

        entryController.getEntryById(id);
    }
}
