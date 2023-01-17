package org.spine.iquestionapi.dto;

import java.util.Set;
import java.util.UUID;

import org.spine.iquestionapi.model.Answer;
import org.spine.iquestionapi.model.Entry;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EntryDto {
    private UUID id;
    private UUID questionnaireId;
    private String questionnaireName;
    private UUID authorId;
    private long timestamp;
    private Set<Answer> answers;

    public EntryDto fromEntry(Entry entry) {
        id = entry.getId();
        questionnaireId = entry.getQuestionnaire().getId();
        questionnaireName = entry.getQuestionnaire().getName();
        authorId = entry.getCaregiver().getId();
        answers = entry.getAnswers();
        timestamp = entry.getTimestamp();
        return this;
    }
}
