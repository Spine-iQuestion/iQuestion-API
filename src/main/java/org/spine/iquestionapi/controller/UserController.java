package org.spine.iquestionapi.controller;

import org.spine.iquestionapi.model.User;
import org.spine.iquestionapi.repository.EmailResetTokenRepo;
import org.spine.iquestionapi.repository.UserRepo;
import org.spine.iquestionapi.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired private UserRepo userRepo;
    @Autowired private EmailResetTokenRepo emailResetTokenRepo;
    @Autowired private AuthorizationService authorizationService;

    // Get all users
    @GetMapping("/all")
    @ResponseBody
    public User[] getAllUsers(){
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

        return userRepo.findById(id).get();
    }

    // Update a user
    @PostMapping("/{id}")
    @ResponseBody
    public User updateUser(@PathVariable(value="id") long id, @RequestBody User user){
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
        emailResetTokenRepo.findByOwner(userToDelete).ifPresent(emailResetTokenRepo::delete);

        // Remove user from userrepo
        userRepo.delete(userToDelete);
    }
}
