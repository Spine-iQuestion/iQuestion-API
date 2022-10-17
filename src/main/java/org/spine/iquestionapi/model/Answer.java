package org.spine.iquestionapi.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "answers")
public class Answer implements Model {
    @Id
    @GeneratedValue
    public long id;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public Question question;
    public String answer;

    public Answer() {
    }

    @Override
    public String serialize() {
        // TODO Auto-generated method stub
        return null;
    }
}
