package org.spine.iquestionapi.dto;

import java.util.Optional;
import java.util.Set;

import org.hibernate.Hibernate;
import org.spine.iquestionapi.model.Entry;
import org.spine.iquestionapi.model.Questionnaire;
import org.spine.iquestionapi.model.Segment;
import org.spine.iquestionapi.repository.EntryRepo;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionnaireDto {
    @JsonIgnore
    private EntryRepo entryRepo;

    public String id;
    public String name;
    public AuthorDto author;
    public long timestamp;
    public Integer entryCount;
    public long lastEntryTimestamp;
    public Set<Segment> segments;

    public QuestionnaireDto(EntryRepo entryRepo) {
        this.entryRepo = entryRepo;
    }

    public QuestionnaireDto fromQuestionnaire(Questionnaire questionnaire) {
        id = questionnaire.getId().toString();
        name = questionnaire.getName();
        // sometimes, segments are not set for performance reasons
        if (Hibernate.isInitialized(questionnaire.getSegments())) {
            segments = questionnaire.getSegments();
        }
        author = new AuthorDto().fromUser(questionnaire.getAuthor());
        timestamp = questionnaire.getTimestamp();
        entryCount = entryRepo.countByQuestionnaireId(questionnaire.getId());

        Optional<Entry> lastEntry = entryRepo.findTopByQuestionnaireOrderByTimestampDesc(questionnaire);
        if (lastEntry.isPresent()) {
            lastEntryTimestamp = lastEntry.get().getTimestamp();
        }

        return this;
    }
}
