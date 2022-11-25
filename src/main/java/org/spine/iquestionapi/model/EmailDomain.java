package org.spine.iquestionapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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
    /**
     * The id of the email domain
     */
    @Id
    @GeneratedValue
    private long id;

    /**
     * The domain for an email
     */
    public String domain;
}
