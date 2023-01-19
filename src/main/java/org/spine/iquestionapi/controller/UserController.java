package org.spine.iquestionapi.controller;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.spine.iquestionapi.model.User;
import org.spine.iquestionapi.repository.EmailResetTokenRepo;
import org.spine.iquestionapi.repository.UserRepo;
import org.spine.iquestionapi.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
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
    @PersistenceContext
	private EntityManager entityManager;
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
    public User getUserById(@PathVariable(value="id") UUID id){
        // Check if user is looking for himself
        if (authorizationService.getLoggedInUser().getId() == id || authorizationService.getLoggedInUser().getRole() == User.Role.SPINE_ADMIN) {
            return userRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "USER_NOT_FOUND"));
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
    public User updateUser(@PathVariable(value="id") UUID id, @RequestBody User user){
        User userToUpdate = userRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "USER_NOT_FOUND"));
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
    public void deleteUser(@PathVariable(value="id") UUID id){
        // Get user to delete
        User userToDelete = userRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "USER_NOT_FOUND"));
        emailResetTokenRepo.findByOwner(userToDelete).ifPresent(emailResetTokenRepo::delete);

        // Remove user from userrepo
        userRepo.delete(userToDelete);
    }
}
