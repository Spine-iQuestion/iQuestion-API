package org.spine.iquestionapi.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
    /**
     * The id of the questionnaire
     */
    @Id
    @GeneratedValue
    private long id;
    /**
     * The name of the questionnaire
     */
    @NotNull
    private String name;
    /**
     * The questions in the questionnaire
     */
    @OneToMany(cascade = CascadeType.ALL)
    private List<Segment> segments = new ArrayList<>();
    
}
