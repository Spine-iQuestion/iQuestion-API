package org.spine.iquestionapi.repository;

import org.spine.iquestionapi.model.Entry;
import org.spine.iquestionapi.model.Questionnaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

/**
 * The repository for the entry
 */
public interface EntryRepo extends JpaRepository<Entry, UUID> {
    /**
     * Find filled in entries by a questionnaire
     * @param questionnaire the questionnaire
     * @return the entries
     */
    public Optional<ArrayList<Entry>> findByQuestionnaire(Questionnaire questionnaire);
}