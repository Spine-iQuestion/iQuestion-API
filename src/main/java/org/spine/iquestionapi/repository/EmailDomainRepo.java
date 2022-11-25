package org.spine.iquestionapi.repository;

import org.spine.iquestionapi.model.EmailDomain;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The repository for the EmailDomain
 */
public interface EmailDomainRepo extends JpaRepository<EmailDomain, Long> {

}