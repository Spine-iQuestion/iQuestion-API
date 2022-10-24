package org.spine.iquestionapi.model;

import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "password_tokens")
@AllArgsConstructor
@NoArgsConstructor

public class PasswordToken {
    @Id
    @GeneratedValue
    private long id;
    private String token;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private User owner;
    private String password;

    public static int TOKEN_LENGTH = 32;
}
