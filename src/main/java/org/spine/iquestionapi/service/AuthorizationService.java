package org.spine.iquestionapi.service;

import org.spine.iquestionapi.model.User;
import org.spine.iquestionapi.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * The service for authorization
 */
@Component
public class AuthorizationService {

    @Autowired private UserRepo userRepo;

    /**
     * Get the logged in user
     * @return the logged in user
     */
    public User getLoggedInUser() {
        if (userRepo.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).isPresent()) {
            return userRepo.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        }

        return null;
    }
}
