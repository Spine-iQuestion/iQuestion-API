package org.spine.iquestionapi.controller;

import org.spine.iquestionapi.model.ChangePassword;
import org.spine.iquestionapi.model.EmailResetToken;
import org.spine.iquestionapi.model.LoginCredentials;
import org.spine.iquestionapi.model.User;
import org.spine.iquestionapi.repository.EmailResetTokenRepo;
import org.spine.iquestionapi.repository.UserRepo;
import org.spine.iquestionapi.security.JWTUtil;
import org.spine.iquestionapi.service.AuthorizationService;
import org.spine.iquestionapi.service.EmailSenderService;
import org.spine.iquestionapi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired private UserRepo userRepo;
    @Autowired private EmailResetTokenRepo passwordTokenRepo;
    @Autowired private JWTUtil jwtUtil;
    @Autowired private AuthenticationManager authManager;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private AuthorizationService authorizationService;

    @Autowired private EmailSenderService emailSenderService;

    @PostMapping("/register")
    @ResponseBody
    public Map<String, String> register(@RequestBody User user){
        // Check if email is of a valid type
        if(!StringUtil.isValidEmail(user.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email");
        }

        // Check if password is safe
        if(!StringUtil.isSafePassword(user.getPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is not safe");
        }

        // Check if the email already exists
        if (userRepo.findByEmail(user.getEmail()).isPresent()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "This email already exists"
            );
        }

        // Encode the password
        String encodedPass = passwordEncoder.encode(user.getPassword());

        // Save encoded password
        user.setPassword(encodedPass);
        user = userRepo.save(user);

        // Generate and return the token
        String token = jwtUtil.generateToken(user.getEmail());

        return Collections.singletonMap("jwt-token", token);
    }

    @PostMapping("/login")
    @ResponseBody
    public Map<String, Object> login(@RequestBody LoginCredentials body){
        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword());

            authManager.authenticate(authInputToken);

            String token = jwtUtil.generateToken(body.getEmail());

            return Collections.singletonMap("jwt-token", token);
        }catch (AuthenticationException authExc) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials.");
        }
    }

    @PostMapping("/reset-password")
    @ResponseBody
    public Map<String, Object> resetPassword(@RequestBody User user){
        // Check if user is the same as the loggen in user
        if(!user.getEmail().equals(authorizationService.getLoggedInUser().getEmail())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "That's not your email!");
        }

        // Generate a token and save it to the database
        EmailResetToken token = new EmailResetToken();
        token.setToken(StringUtil.generateRandomString(EmailResetToken.TOKEN_LENGTH));
        token.setOwner(user);
        passwordTokenRepo.save(token);

        // Send token via email
        // TODO: no template yet for email
        try {
            emailSenderService.sendSimpleEmail(user.getEmail(), "Reset Password", token.getToken().toString());
        } catch (MessagingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An internal server error has occurred.");
        }

        return Collections.singletonMap("status", "Sent token to email");
    }

    @PostMapping("/change-password")
    @ResponseBody
    @Transactional
    public Map<String, Object> changePassword(@RequestBody ChangePassword credentials) {
        EmailResetToken tokenFromDb = passwordTokenRepo.findByToken(credentials.getToken()).get();
        if (tokenFromDb.getToken() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token not found or invalid.");
        }

        User user = tokenFromDb.getOwner();
        user.setPassword(passwordEncoder.encode(credentials.getNewPassword()));
        userRepo.save(user);
        passwordTokenRepo.removeAllByToken(tokenFromDb.getToken());

        return Collections.singletonMap("status", "Token changed");
    }
}