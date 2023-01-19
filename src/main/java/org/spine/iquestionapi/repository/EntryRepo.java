package org.spine.iquestionapi.repository;

import org.spine.iquestionapi.model.Entry;
import org.spine.iquestionapi.model.Questionnaire;
import org.spine.iquestionapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * The repository for the entry
 */
public interface EntryRepo extends JpaRepository<Entry, UUID> {
    /**
     * Find filled in entries by a questionnaire
     * 
     * @param questionnaire the questionnaire
     * @return the entries
     */
    public Optional<Set<Entry>> findByQuestionnaire(Questionnaire questionnaire);

    public Integer countByQuestionnaireId(UUID questionnaireId);

    public Optional<Set<Entry>> findByQuestionnaireId(UUID questionnaireId);

    public Optional<Entry> findTopByQuestionnaireOrderByTimestampDesc(Questionnaire questionnaire);

    public Optional<Set<Entry>> findByCaregiver(User user);

    public Optional<Entry> findByIdAndCaregiver(UUID id, User user);
}