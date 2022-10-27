package org.spine.iquestionapi.model;

import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "email_reset_token")
@AllArgsConstructor
@NoArgsConstructor

public class EmailResetToken {
    @Id
    @GeneratedValue
    private long id;
    private String token;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(unique = true, nullable = false)
    private User owner;

    public static int TOKEN_LENGTH = 32;
}
