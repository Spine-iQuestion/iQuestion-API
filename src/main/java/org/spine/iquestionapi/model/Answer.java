package org.spine.iquestionapi.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The answer to a question on a questionnaire
 */
@Getter
@Setter
@Entity
@Table(name = "answers")
@AllArgsConstructor
@NoArgsConstructor
public class Answer {
    /**
     * The id of the answer
     */
    @Id
    @GeneratedValue
    public long id;
    /**
     * The question the answer is for
     */
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public Question question;
    /**
     * The answer to the question
     */
    public String result;
    /**
     * An explanation of the answer
     */
    public String comment;
}
