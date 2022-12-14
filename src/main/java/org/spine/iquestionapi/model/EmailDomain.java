package org.spine.iquestionapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * The domain for an email
     */
    private String domain;
}
