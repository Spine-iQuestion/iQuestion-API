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
@Table(name = "scoremodel")
@AllArgsConstructor
@NoArgsConstructor
public class Scoremodel{
    enum Result {
        PRESENT, PLAUSIBLE, ABSENT
    }

    @Id
    @GeneratedValue
    private long id;
    // TODO: fix the hashmap for the database
    //private Map<Segment,Result> condition = new HashMap<Segment,Result>();
    private String result;

}
