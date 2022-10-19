package org.spine.iquestionapi.repository;

import java.util.Optional;

import org.spine.iquestionapi.model.PasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordTokenRepo extends JpaRepository<PasswordToken, Long> {
    public Optional<PasswordToken> findByToken(String token);
}