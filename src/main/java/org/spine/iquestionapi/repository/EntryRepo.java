package org.spine.iquestionapi.repository;

import org.spine.iquestionapi.model.EmailResetToken;
import org.spine.iquestionapi.model.Entry;
import org.spine.iquestionapi.model.Questionnaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface EntryRepo extends JpaRepository<Entry, Long> {
    public Optional<ArrayList<Entry>> findByQuestionnaire(Questionnaire questionnaire);
}