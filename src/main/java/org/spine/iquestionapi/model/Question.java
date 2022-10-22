package org.spine.iquestionapi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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
