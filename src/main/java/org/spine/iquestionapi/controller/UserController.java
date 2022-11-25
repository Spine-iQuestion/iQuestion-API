package org.spine.iquestionapi.controller;

import org.spine.iquestionapi.model.User;
import org.spine.iquestionapi.repository.EmailResetTokenRepo;
import org.spine.iquestionapi.repository.UserRepo;
import org.spine.iquestionapi.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * The controller for the user
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired private UserRepo userRepo;
    @Autowired private EmailResetTokenRepo emailResetTokenRepo;
    @Autowired private AuthorizationService authorizationService;
    
    /**
     * Get all users
     * @return a list of all users
     */
    @GetMapping("/all")
    @ResponseBody
    public User[] getAllUsers(){
        return userRepo.findAll().toArray(new User[0]);
    }

    /**
     * Get the logged in user
     * @return the logged in user
     */
    @GetMapping("/me")
    @ResponseBody
    public User getLoggedInUser(){
        return authorizationService.getLoggedInUser();
    }

    /**
     * Get a user by id
     * @param id the id of the user
     * @return the user
     */
    @GetMapping("/{id}")
    @ResponseBody
    public User getUserById(@PathVariable(value="id") long id){
        // Check if user is looking for himself
        if (authorizationService.getLoggedInUser().getId() == id) {
            return userRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The user was not found"));
        }
      return null;
    }

    /**
     * Update a user
     * @param id the id of the user to be updated
     * @param user the user to be updated
     * @return the updated user
     */
    @PostMapping("/{id}")
    @ResponseBody
    public User updateUser(@PathVariable(value="id") long id, @RequestBody User user){
        User userToUpdate = userRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The user was not found"));
        // Update fields that are given
        if (user.getName() != null) userToUpdate.setName(user.getName());
        if (user.getRole() != null) userToUpdate.setRole(user.getRole());
        if (user.getOrganization() != null) userToUpdate.setOrganization(user.getOrganization());

        return userRepo.save(userToUpdate);
    }

    /**
     * Delete a user
     * @param id the id of the user to be deleted
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteUser(@PathVariable(value="id") long id){
        // Get user to delete
        User userToDelete = userRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The user was not found"));
        emailResetTokenRepo.findByOwner(userToDelete).ifPresent(emailResetTokenRepo::delete);

        // Remove user from userrepo
        userRepo.delete(userToDelete);
    }
}
