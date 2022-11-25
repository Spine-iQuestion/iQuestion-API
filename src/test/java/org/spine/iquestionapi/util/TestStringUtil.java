package org.spine.iquestionapi.util;

import org.junit.Before;
import org.junit.Test;
import org.spine.iquestionapi.model.EmailDomain;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestStringUtil {

    private List<EmailDomain> validEmailDomains;

    @Before
    public void setUp() {
        this.validEmailDomains = new ArrayList<>();
        validEmailDomains.add(new EmailDomain(1, "gmail.com"));
        validEmailDomains.add(new EmailDomain(2, "test.com"));
    }

    @Test
    public void Should_ReturnTrue_When_emailIsValid() {
        //Arrange
        String email = "testemail@test.com";

        //Asserts
        assertTrue(StringUtil.isValidEmail(email, validEmailDomains));
    }

    @Test
    public void Should_ReturnFalse_When_emailIsInvalid() {
        //Arrange
        String email = "testemail@domain.com";

        //Asserts
        assertFalse(StringUtil.isValidEmail(email, validEmailDomains));
    }
}
