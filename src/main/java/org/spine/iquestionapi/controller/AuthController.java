package org.spine.iquestionapi.controller;

import org.apache.tomcat.util.bcel.Const;
import org.spine.iquestionapi.model.*;
import org.spine.iquestionapi.repository.EmailDomainRepo;
import org.spine.iquestionapi.repository.EmailResetTokenRepo;
import org.spine.iquestionapi.repository.UserRepo;
import org.spine.iquestionapi.security.JWTUtil;
import org.spine.iquestionapi.service.EmailSenderService;
import org.spine.iquestionapi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import freemarker.template.TemplateException;

import javax.mail.MessagingException;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The controller for authentication
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private EmailResetTokenRepo passwordTokenRepo;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailDomainRepo emailDomainRepo;
    @Autowired
	private EmailSenderService emailSenderService;
    @Autowired
    private Environment env;

    final long ninetyDaysInMilliseconds = 7776000000L;

    /**
     * Register a new user
     *
     * @param user the user to be registered
     * @return the generated jwt token
     */
    @PostMapping("/register")
    @ResponseBody
    public Map<String, String> register(@RequestBody User user) {
        if (user.getPassword() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "SET_PASSWORD_NOT_ALLOWED");
        }

        List<EmailDomain> emailDomains = emailDomainRepo.findAll();

        // Check if email is of a valid type
        if (!StringUtil.isValidEmail(user.getEmail(), emailDomains)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "INVALID_EMAIL");
        }


        // Check if the email already exists
        if (userRepo.findByEmail(user.getEmail()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "EMAIL_EXISTS"
            );
        }
        user = userRepo.save(user);
        EmailResetToken tokenEntity = new EmailResetToken();
        String token = StringUtil.generateRandomString(EmailResetToken.TOKEN_LENGTH);
        tokenEntity.setToken(token);
        tokenEntity.setOwner(user);

        passwordTokenRepo.save(tokenEntity);
        
        requestPasswordReset(new RequestPasswordResetBody(user.getEmail()));

        return Collections.singletonMap("status", "succes");
    }

    /**
     * Login a user
     *
     * @param body the user credentials
     * @return the generated jwt token
     */
    @PostMapping("/login")
    @ResponseBody
    public Map<String, Object> login(@RequestBody LoginCredentials body) {
        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword());
            User user = userRepo.findByEmail(body.getEmail()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "USER_NOT_FOUND"));
            if (!user.isEnabled()) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "USER_DISABLED");
            }
            authManager.authenticate(authInputToken);

            
            if(user.getPasswordChangeTime() <= ninetyDaysInMilliseconds){
                requestPasswordReset(new RequestPasswordResetBody(user.getEmail()));
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "RESET_PASSWORD");
            }

            String token = jwtUtil.generateToken(body.getEmail());

            return Map.of(
                    "token", token,
                    "user", user
            );
        } catch (AuthenticationException authExc) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "INVALID_CREDENTIALS");
        }
    }

    /**
     * Request a password reset
     *
     * @param requestPasswordResetBody a RequestPasswordResetBody containing the users email
     * @return success message
     * @throws TemplateException
     * @throws IOException
     */
    @PostMapping("/request-password-reset")
    @ResponseBody
    public Map<String, Object> requestPasswordReset(@RequestBody RequestPasswordResetBody requestPasswordResetBody){
        // Get user from email
        User user = userRepo.findByEmail(requestPasswordResetBody.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "EMAIL_NOT_FOUND"));

        // Check if user already has token
        if (passwordTokenRepo.findByOwner(user).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "TOKEN_EXISTS");
        }

        EmailResetToken tokenEntity = new EmailResetToken();
        String token = StringUtil.generateRandomString(EmailResetToken.TOKEN_LENGTH);
        tokenEntity.setToken(token);
        tokenEntity.setOwner(user);

        passwordTokenRepo.save(tokenEntity);

        try {
            Map<String, Object> model = new HashMap<>();
            String webString = env.getProperty("spine.emailsender.websiteurl");

            model.put("action_url", webString + "/reset-password/" + token);
            model.put("name", user.getName());

            emailSenderService.sendEmail(requestPasswordResetBody, model);

        } catch (MessagingException | IOException | TemplateException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR");
        }
        return Collections.singletonMap("status", "Sent token to email");
    }

    /**
     * Change the password of a user using the token generated with request-password-reset
     *
     * @param credentials the token and the new password
     * @return success message
     */
    @PostMapping("/change-password")
    @ResponseBody
    @Transactional
    public Map<String, Object> changePassword(@RequestBody ResetPasswordBody credentials) {
        EmailResetToken tokenFromDb = passwordTokenRepo.findByToken(credentials.getToken()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "TOKEN_NOT_FOUND"));

        User user = tokenFromDb.getOwner();
        user.setPassword(passwordEncoder.encode(credentials.getNewPassword()));
        user.setEnabled(true);
        user.setPasswordChangeTime(System.currentTimeMillis());
        userRepo.save(user);
        passwordTokenRepo.removeByToken(tokenFromDb.getToken());

        return Collections.singletonMap("status", "Password changed");
    }
}
