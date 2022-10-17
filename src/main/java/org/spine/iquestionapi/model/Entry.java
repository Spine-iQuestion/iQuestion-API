package org.spine.iquestionapi.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "entry")
public class Entry implements Model {
    @Id
    @GeneratedValue
    private int id;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Questionnaire questionnaire;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private User caregiver;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Answer> answers = new ArrayList<>();

    public Entry() {
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }

    @Override
    public String serialize() {
        // TODO Auto-generated method stub
        return null;
    }
}
