package org.spine.iquestionapi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.spine.iquestionapi.service.EntityIdResolver;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The question on a questionnaire
 */
@Getter
@Setter
@Entity
@Table(name = "question")
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(
   generator = ObjectIdGenerators.PropertyGenerator.class,
   property = "id",
   resolver = EntityIdResolver.class,
scope = Question.class
   )
public class Question {
    enum Type {
        OPEN_CLIENT, CLOSED_CLIENT, OPEN_CAREGIVER
    }

    /**
     * The id of the question
     */
    @Id
    @GeneratedValue
    private long id;
    /**
     * The question
     */
    private String label;
    /**
     * The type of the question
     */
    private Type type;
}
