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
        OPEN, CLOSED
    }

    @Id
    @GeneratedValue
    private long id;
    private String label;
    private Type type;


}
