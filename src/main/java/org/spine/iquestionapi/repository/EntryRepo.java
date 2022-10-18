package org.spine.iquestionapi.repository;

import org.spine.iquestionapi.model.Entry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EntryRepo extends JpaRepository<Entry, Long> {
    
}