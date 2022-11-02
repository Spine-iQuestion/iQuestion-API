package org.spine.iquestionapi.repository;

import org.spine.iquestionapi.model.EmailResetToken;
import org.spine.iquestionapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailResetTokenRepo extends JpaRepository<EmailResetToken, Long> {
    Optional<EmailResetToken> findByToken(String token);

    Optional<EmailResetToken> findByOwner(User user);

    void removeByToken(String token);
}