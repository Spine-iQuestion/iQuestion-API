package org.spine.iquestionapi.repository;

import java.util.Optional;

import org.spine.iquestionapi.model.EmailDomain;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The repository for the EmailDomain
 */
public interface EmailDomainRepo extends JpaRepository<EmailDomain, String> {
    Optional<EmailDomain> findByDomain(String domain);
}