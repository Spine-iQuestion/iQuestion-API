package org.spine.iquestionapi.dto;

import org.spine.iquestionapi.model.User;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto {
    public String name;
    public String organization;

    public AuthorDto fromUser(User user) {
        name = user.getName();
        organization = user.getOrganization();
        return this;
    }
}
