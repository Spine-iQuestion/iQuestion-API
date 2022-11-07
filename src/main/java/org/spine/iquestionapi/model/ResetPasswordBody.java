package org.spine.iquestionapi.model;

import lombok.*;

/**
 * This is the body for a change password request
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordBody {
    /**
     * The token the user received in his email
     */
    private String token;
    /**
     * The new password
     */
    private String newPassword;
}
