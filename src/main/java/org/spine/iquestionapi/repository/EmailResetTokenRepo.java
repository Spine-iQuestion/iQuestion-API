package org.spine.iquestionapi.repository;

import java.util.Optional;

import org.spine.iquestionapi.model.EmailResetToken;
import org.spine.iquestionapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailResetTokenRepo extends JpaRepository<EmailResetToken, Long> {
    public Optional<EmailResetToken> findByToken(String token);
    public Optional<EmailResetToken> findByOwner(User user);
    public Optional<EmailResetToken> removeEmailResetTokenByToken(EmailResetToken token);
}