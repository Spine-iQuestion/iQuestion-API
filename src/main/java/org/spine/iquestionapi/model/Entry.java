package org.spine.iquestionapi.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

/**
 * An entry is a questionnaire filled in by a caregiver
 */
@Getter
@Setter
@Entity
@Table(name = "entry")
@AllArgsConstructor
@NoArgsConstructor
public class Entry  {

    @Id
    @Column(name = "id")
    @Type(type = "uuid-char")
    private UUID id = UUID.randomUUID();
    /**
     * The questionnaire the entry is for
     */
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Questionnaire questionnaire;
    /**
     * The user who filled in the questionnaire
     */
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "caregiver_id")
    private User caregiver;
    /**
     * The answers to the questions in the questionnaire
     */
    @OneToMany(cascade = CascadeType.ALL)
    private List<Answer> answers = new ArrayList<>();
    /**
     * The date the entry was created
     */
    private long timestamp;
}
