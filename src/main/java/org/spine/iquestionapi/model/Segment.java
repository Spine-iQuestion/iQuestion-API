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

/**
 * A segment is a part of a questionnaire
 */
@Getter
@Setter
@Entity
@Table(name = "segment")
@AllArgsConstructor
@NoArgsConstructor
public class Segment {
    /**
     * The id of the segment
     */
    @Id
    @GeneratedValue
    private long id;
    /**
     * The name of the segment
     */
    private String title;
    /**
     * The description of the segment
     */
    private String description;
    /**
     * The questions in the segment
     */
    @OneToMany(cascade = CascadeType.ALL)
    private List<Question> questions = new ArrayList<>();
}
