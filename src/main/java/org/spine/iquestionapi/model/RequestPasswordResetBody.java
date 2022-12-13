package org.spine.iquestionapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestPasswordResetBody {
  /**
   * The email of the user
   */
  private String email;
}
