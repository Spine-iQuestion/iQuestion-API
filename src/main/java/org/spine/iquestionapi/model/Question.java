package org.spine.iquestionapi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "question")
public class Question implements Model {
    enum Type {
        OPEN, CLOSED
    }

    @Id
    @GeneratedValue
    private long id;
    private String label;
    private Type type;

    public Question() {
    }

    @Override
    public String serialize() {
        // TODO Auto-generated method stub
        return null;
    }
}
