package org.spine.iquestionapi.service;

import org.spine.iquestionapi.model.User;
import org.spine.iquestionapi.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationService {

    @Autowired private UserRepo userRepo;

    public User getLoggedInUser() {
        if (userRepo.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).isPresent()) {
            return userRepo.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        }

        return null;
    }
}
