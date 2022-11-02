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
    @GeneratedValue
    private long id;
    @NotNull
    private String name;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Segment> segments = new ArrayList<>();
    
}
