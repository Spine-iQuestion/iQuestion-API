package org.spine.iquestionapi.repository;

import org.spine.iquestionapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * The repository for the user
 */
public interface UserRepo extends JpaRepository<User, Long> {
    /**
     * Find a user by its email
     * @param email the email
     * @return the user
     */
    public Optional<User> findByEmail(String email);
    
}