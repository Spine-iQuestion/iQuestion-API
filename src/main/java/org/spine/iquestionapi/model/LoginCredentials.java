package org.spine.iquestionapi.model;

import lombok.*;

/**
 * This is the body for a login request
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginCredentials {
    public static final int MIN_LENGTH_PASSWORD = 8;

    /**
     * The username of the user
     */
    private String email;
    /**
     * The password of the user
     */
    private String password;
}