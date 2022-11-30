package org.spine.iquestionapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

/**
 * The valid domains for an email
 */
@Getter
@Setter
@Entity
@Table(name = "email_domain")
@AllArgsConstructor
@NoArgsConstructor
public class EmailDomain {

    @Id
    @Column(name = "id")
    @Type(type = "uuid-char")
    private UUID id = UUID.randomUUID();

    /**
     * The domain for an email
     */
    public String domain;
}
