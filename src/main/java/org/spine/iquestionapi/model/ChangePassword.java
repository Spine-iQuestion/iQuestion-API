package org.spine.iquestionapi.model;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChangePassword {

    @Id
    @GeneratedValue
    private long id;

    private String token;

    private String newPassword;
}
