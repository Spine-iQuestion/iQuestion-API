package org.spine.iquestionapi.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordBody {
    private String token;
    private String newPassword;
}
