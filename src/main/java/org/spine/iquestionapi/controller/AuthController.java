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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @PostMapping("/register")
    @ResponseBody
    public Map<String, Object> registerHandler(@RequestBody User user){
        // Check if the email already exists
        if (userRepo.findByEmail(user.getEmail()) != null){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "This email already exists"
            );
        }
        String encodedPass = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPass);
        user = userRepo.save(user);
        String token = jwtUtil.generateToken(user.getEmail());
        return Collections.singletonMap("jwt-token", token);
    }

    @PostMapping("/login")
    @ResponseBody
    public Map<String, Object> loginHandler(@RequestBody LoginCredentials body){
        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword());

            authManager.authenticate(authInputToken);

            String token = jwtUtil.generateToken(body.getEmail());

            return Collections.singletonMap("jwt-token", token);
        }catch (AuthenticationException authExc){
            authExc.printStackTrace();
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/reset-password")
    @ResponseBody
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
        EmailSenderService emailSenderService = new EmailSenderService();
        emailSenderService.sendSimpleEmail(user.getEmail(), "Reset Password", "Your token is: " + token.getToken());

        return true;
    }

    // TODO: verify user
    @PostMapping("/change-password")
    public boolean changePassword(@RequestBody PasswordToken token){
        PasswordToken tokenFromDb = passwordTokenRepo.findByToken(token.getToken()).get();
        if (tokenFromDb == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid token");
        }

        User user = tokenFromDb.getOwner();
        user.setPassword(passwordEncoder.encode(token.getPassword()));
        userRepo.save(user);

        throw new ResponseStatusException(HttpStatus.OK,"Password Changed");
    }




}