package org.spine.iquestionapi.model;

import javax.persistence.*;

import org.hibernate.annotations.Type;
import org.spine.iquestionapi.service.EntityIdResolver;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

/**
 * The user of the application
 */
@Getter
@Setter
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(
   generator = ObjectIdGenerators.PropertyGenerator.class,
   property = "id",
   resolver = EntityIdResolver.class,
    scope = User.class
   )
public class User {
    /**
     * The roels a user can have
     */
    public enum Role {
        /** A Spine admin */
        SPINE_ADMIN,
        /** A Spine user */
        SPINE_USER,
        /** A caregiver */
        CAREGIVER
    }

    @Id
    @Column(name = "id")
    @Type(type = "uuid-char")
    private UUID id = UUID.randomUUID();
    /**
     * The name of the user
     */
    private String name;
    /**
     * The email of the user
     */
    @Column(unique=true)
    private String email;
    /**
     * The password of the user
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    /**
     * The organization of the user
     */
    private String organization;
    /**
     * The state of activation of the account
     */
    @JsonIgnore
    private boolean enabled = false;
    /**
     * The entries a user filled in
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "caregiver", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Entry> entries;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
    @JsonIgnore
    private List <Questionnaire> questionnaires;
    /**
     * The role of the user
     */
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    @JsonIgnore
    private long passwordChangeTime = 0;
}
