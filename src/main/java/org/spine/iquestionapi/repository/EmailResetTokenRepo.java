package org.spine.iquestionapi.repository;

import org.spine.iquestionapi.model.EmailResetToken;
import org.spine.iquestionapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * The repository for the email reset token
 */
public interface EmailResetTokenRepo extends JpaRepository<EmailResetToken, UUID> {
    /**
     * Find token details by a token
     * @param token the token
     * @return the token details
     */
    Optional<EmailResetToken> findByToken(String token);

    /**
     * Find token details by a user
     * @param user the user
     * @return the token details
     */
    Optional<EmailResetToken> findByOwner(User user);

    /**
     * Delete token details by a token
     * @param token the token
     */
    void removeByToken(String token);
}