package org.spine.iquestionapi.repository;

import org.spine.iquestionapi.model.Questionnaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * The repository for the questionnaire
 */
public interface QuestionnaireRepo extends JpaRepository<Questionnaire, Long> {

    /**
     * Find a questionnaire by its id
     * @param questionnaire the questionnaire object containing the id
     * @return the questionnaire
     */
    public Optional<Questionnaire[]> findById(Questionnaire questionnaire);
}