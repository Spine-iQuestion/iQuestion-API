package org.spine.iquestionapi.model;

import javax.persistence.*;
import lombok.*;

/**
 * This is the body for a request to reset the password
 */
@Getter
@Setter
@Entity
@Table(name = "email_reset_token")
@AllArgsConstructor
@NoArgsConstructor
public class EmailResetToken {
    @Id
    @GeneratedValue
    /**
     * The id of the token
     */
    private long id;
    /**
     * The token
     */
    private String token;
    /**
     * The user the token is for
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(unique = true, nullable = false)
    private User owner;

    /**
     * The length of a token
     */
    public static int TOKEN_LENGTH = 32;
}
