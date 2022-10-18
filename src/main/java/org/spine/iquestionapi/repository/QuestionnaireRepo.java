package org.spine.iquestionapi.repository;

import org.spine.iquestionapi.model.Questionnaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionnaireRepo extends JpaRepository<Questionnaire, Long> {
    
}