package org.spine.iquestionapi.controller;

import java.util.Objects;

import org.spine.iquestionapi.model.User;
import org.spine.iquestionapi.repository.EmailResetTokenRepo;
import org.spine.iquestionapi.repository.UserRepo;
import org.spine.iquestionapi.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/user")
// TODO : dont send password in response
public class UserController {

    @Autowired private UserRepo userRepo;
    @Autowired private EmailResetTokenRepo emailResetTokenRepo;
    @Autowired private AuthorizationService authorizationService;

    // Get all users
    @GetMapping("/all")
    @ResponseBody
    public User[] getAllUsers(){
        // Check if logged in user is admin
        User loggedInUser = authorizationService.getLoggedInUser();
        if (Objects.isNull(loggedInUser)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not logged in.");
        }

        if (loggedInUser.getRole() != User.Role.SPINE_ADMIN) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not an admin.");
        }

        return userRepo.findAll().toArray(new User[0]);
    }

    @GetMapping("/me")
    @ResponseBody
    public User getLoggedInUser(){
        return authorizationService.getLoggedInUser();
    }

    // Get a user by id
    @GetMapping("/{id}")
    @ResponseBody
    public User getUserById(@PathVariable(value="id") long id){
        // Check if user is looking for himself
        if (authorizationService.getLoggedInUser().getId() == id) {
            return userRepo.findById(id).get();
        }
        
        // Check if logged in user is admin
        if (authorizationService.getLoggedInUser().getRole() != User.Role.SPINE_ADMIN) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not an admin.");
        }

        return userRepo.findById(id).get();
    }

    // Update a user
    @PostMapping("/{id}")
    @ResponseBody
    public User updateUser(@PathVariable(value="id") long id, @RequestBody User user){
        // Check if logged in user is admin
        if (authorizationService.getLoggedInUser().getRole() != User.Role.SPINE_ADMIN) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not an admin.");
        }

        User userToUpdate = userRepo.findById(id).get();
        // Update fields that are given
        if (user.getName() != null) userToUpdate.setName(user.getName());
        if (user.getRole() != null) userToUpdate.setRole(user.getRole());
        if (user.getOrganization() != null) userToUpdate.setOrganization(user.getOrganization());

        return userRepo.save(userToUpdate);
    }

    // Delete a user
    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteUser(@PathVariable(value="id") long id){
        // Get user to delete
        User userToDelete = userRepo.findById(id).get();
        // TODO: check all cascading. The function doenst even need to delete from userrepo now
        emailResetTokenRepo.findByOwner(userToDelete).ifPresent(emailResetTokenRepo::delete);
    }
}
