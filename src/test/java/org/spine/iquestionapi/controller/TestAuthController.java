package org.spine.iquestionapi.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.spine.iquestionapi.model.LoginCredentials;
import org.spine.iquestionapi.model.User;
import org.spine.iquestionapi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestAuthController {

    @Autowired private AuthController authController;

    @Test
    public void testCorrectLogin() {
        // Arrange
        LoginCredentials loginCredentials = new LoginCredentials("caregiver@test.com", "123456789");

        // Act
        Map<String, Object> result = authController.login(loginCredentials);

        // Assert
        assertNotNull(result.get("jwt-token"));
    }

    @Test(expected = ResponseStatusException.class)
    public void testIncorrectLogin() {
        // Arrange
        LoginCredentials loginCredentials = new LoginCredentials("testcaregiver", "BADPASSWORD");

        authController.login(loginCredentials);
    }

    @Test
    public void testCorrectRegister() {
        // Arrange
        User user = new User();
        // Generate random email
        user.setEmail(StringUtil.generateRandomString(15) + "@test.com");
        user.setPassword("123456789");
        user.setOrganization("test");
        user.setRole(User.Role.CAREGIVER);

        // Act
        Map<String, String> result = authController.register(user);

        // Assert
        assertNotNull(result.get("jwt-token"));
    }

}
