package org.spine.iquestionapi.model;

import javax.persistence.*;

import org.spine.iquestionapi.service.EntityIdResolver;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.UUID;

/**
 * The question on a questionnaire
 */
@Getter
@Setter
@Entity
@Table(name = "question")
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", resolver = EntityIdResolver.class, scope = Question.class)
public class Question {
    @Id
    @Column(name = "id")
    @org.hibernate.annotations.Type(type = "uuid-char")
    private UUID id = UUID.randomUUID();
    private String label;
    private ArrayList<String> options;
}
