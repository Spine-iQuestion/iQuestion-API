package org.spine.iquestionapi.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

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

    @Id
    @Column(name = "id")
    @Type(type = "uuid-char")
    private UUID id = UUID.randomUUID();
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
