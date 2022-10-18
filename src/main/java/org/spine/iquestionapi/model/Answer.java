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

@Getter
@Setter
@Entity
@Table(name = "answers")
@AllArgsConstructor
@NoArgsConstructor
public class Answer implements Model {
    @Id
    @GeneratedValue
    public long id;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public Question question;
    public String answer;

    @Override
    public String serialize() {
        // TODO Auto-generated method stub
        return null;
    }
}
