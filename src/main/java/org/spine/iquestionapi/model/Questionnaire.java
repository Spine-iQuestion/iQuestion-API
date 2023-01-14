package org.spine.iquestionapi.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.*;

import org.hibernate.annotations.Type;
import org.spine.iquestionapi.service.EntityIdResolver;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A questionnaire is a set of questions to be answered by a caregiver, created by a Spine user
 */
@Getter
@Setter
@Entity
@Table(name = "questionnaire")
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(
   generator = ObjectIdGenerators.PropertyGenerator.class,
   property = "id",
   resolver = EntityIdResolver.class,
   scope=Questionnaire.class
   )
public class Questionnaire {
    @Id
    @Column(name = "id")
    @Type(type = "uuid-char")
    private UUID id = UUID.randomUUID();
    /**
     * The name of the questionnaire
     */
    @NotNull
    private String name;
    /**
     * The questions in the questionnaire
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Segment> segments = new HashSet<>();
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private User author;
    private long timestamp;
    private boolean enabled = true;
}
