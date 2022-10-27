package org.spine.iquestionapi.model;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChangePassword {

    @Id
    @JsonIgnore
    @GeneratedValue
    private long id;

    private String token;

    private String newPassword;
}
