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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "entry")
@AllArgsConstructor
@NoArgsConstructor
public class Entry  {
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Questionnaire questionnaire;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private User caregiver;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Answer> answers = new ArrayList<>();
}
