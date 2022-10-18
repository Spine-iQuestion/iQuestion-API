package org.spine.iquestionapi.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "questionnaire")
@AllArgsConstructor
@NoArgsConstructor
public class Questionnaire implements Model {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Segment> segments = new ArrayList<>();
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Scoremodel scoremodel;

    @Override
    public String serialize() {
        // TODO Auto-generated method stub
        return null;
    }
}
