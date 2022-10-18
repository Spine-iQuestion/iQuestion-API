package org.spine.iquestionapi.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "segment")
@AllArgsConstructor
@NoArgsConstructor
public class Segment implements Model {
    @Id
    @GeneratedValue
    private long id;
    private String title;
    private String description;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Question> questions = new ArrayList<>();

    public void addQuestion(Question question) {
        questions.add(question);
    }

    @Override
    public String serialize() {
        // TODO Auto-generated method stub
        return null;
    }
}
