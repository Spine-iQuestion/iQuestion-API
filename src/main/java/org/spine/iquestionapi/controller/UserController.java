package org.spine.iquestionapi.controller;

import org.spine.iquestionapi.model.User;
import org.spine.iquestionapi.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO: There are some changes we need to make to this controller
// 1. The method to register an user is now in the AuthController.
//      We might need to move that method here. But I'm not sure.
// 2. Updating the user's password is not implemented yet.
//      Because it needs to be hashed and all that.
// - Jesse

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired private UserRepo userRepo;

    // Get all users
    @GetMapping("/all")
    public User[] getAllUsers(){
        return userRepo.findAll().toArray(new User[0]);
    }

    // Get an user by id
    @GetMapping("/{id}")
    public User getUserById(@PathVariable(value="id") long id){
        return userRepo.findById(id).get();
    }

    // Update an user
    @PostMapping("/{id}")
    public User updateUser(@PathVariable(value="id") long id, @RequestBody User user){
        User userToUpdate = userRepo.findById(id).get();
        // Update fields that are given
        if (user.getName() != null) userToUpdate.setName(user.getName());
        if (user.getRole() != null) userToUpdate.setRole(user.getRole());

        return userRepo.save(userToUpdate);
    }

    // Delete an user
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable(value="id") long id){
        userRepo.deleteById(id);
    }
}
