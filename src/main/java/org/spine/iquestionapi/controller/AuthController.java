package org.spine.iquestionapi.controller;

import org.spine.iquestionapi.model.LoginCredentials;
import org.spine.iquestionapi.model.PasswordToken;
import org.spine.iquestionapi.model.User;
import org.spine.iquestionapi.repository.PasswordTokenRepo;
import org.spine.iquestionapi.repository.UserRepo;
import org.spine.iquestionapi.security.JWTUtil;
import org.spine.iquestionapi.service.EmailSenderService;
import org.spine.iquestionapi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired private UserRepo userRepo;
    @Autowired private PasswordTokenRepo passwordTokenRepo;
    @Autowired private JWTUtil jwtUtil;
    @Autowired private AuthenticationManager authManager;
    @Autowired private PasswordEncoder passwordEncoder;

    @Autowired private EmailSenderService emailSenderService;

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody User user){
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
    public Map<String, Object> login(@RequestBody LoginCredentials body){
        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword());

            authManager.authenticate(authInputToken);

            String token = jwtUtil.generateToken(body.getEmail());

            return Collections.singletonMap("jwt-token", token);
        }catch (AuthenticationException authExc){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials.");
        }
    }

    @PostMapping("/reset-password")
    public boolean resetPassword(@RequestBody User user){
        if (userRepo.findByEmail(user.getEmail()) == null){
            return false;
        }

        // Generate a token and save it to the database
        PasswordToken token = new PasswordToken();
        token.setToken(StringUtil.generateRandomString(PasswordToken.TOKEN_LENGTH));
        token.setOwner(user);
        passwordTokenRepo.save(token);

        // Send token via email
        // TODO: no template yet for email
        try {
            emailSenderService.sendSimpleEmail(user.getEmail(), "Reset Password", token.getToken().toString());
        } catch (MessagingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An internal server error has occurred.");
        }

        return true;
    }

    // TODO: verify user
    @PostMapping("/change-password")
    public boolean changePassword(@RequestBody PasswordToken token){
        PasswordToken tokenFromDb = passwordTokenRepo.findByToken(token.getToken()).get();
        if (tokenFromDb == null){
            return false;
        }

        User user = tokenFromDb.getOwner();
        user.setPassword(passwordEncoder.encode(token.getPassword()));
        userRepo.save(user);

        return true;
    }




}