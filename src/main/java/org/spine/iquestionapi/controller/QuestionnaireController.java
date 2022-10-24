package org.spine.iquestionapi.controller;

import org.spine.iquestionapi.model.User;
import org.spine.iquestionapi.model.Questionnaire;
import org.spine.iquestionapi.repository.QuestionnaireRepo;
import org.spine.iquestionapi.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/questionnaire")
@ResponseStatus(HttpStatus.OK)
public class QuestionnaireController {

    @Autowired private QuestionnaireRepo questionnaireRepo;
    @Autowired private AuthorizationService authorizationService;

    // Get all questionnaires
    @GetMapping("/all")

    public Questionnaire[] getAllQuestionnaires(){
        // Check if logged in user is spine user
        if (authorizationService.getLoggedInUser().getRole() != User.Role.SPINE_USER || authorizationService.getLoggedInUser().getRole() != User.Role.SPINE_ADMIN) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not an employee.");
        }

        return questionnaireRepo.findAll().toArray(new Questionnaire[0]);
    }

    // Get a questionnaire by id
    @GetMapping("/{id}")
    @ResponseBody
    public Questionnaire getQuestionnaireById(@PathVariable(value="id") long id){
        // Check if logged in user is spine user
        if (authorizationService.getLoggedInUser().getRole() != User.Role.SPINE_USER || authorizationService.getLoggedInUser().getRole() != User.Role.SPINE_ADMIN) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not an employee.");
        }

        return questionnaireRepo.findById(id).get();
    }

    // Create a questionnaire
    @PutMapping("/")
    @ResponseBody
    public Questionnaire createQuestionnaire(@RequestBody Questionnaire questionnaire){
        // Check if logged in user is spine user
        if (authorizationService.getLoggedInUser().getRole() != User.Role.SPINE_USER || authorizationService.getLoggedInUser().getRole() != User.Role.SPINE_ADMIN) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not an employee.");
        }

        return questionnaireRepo.save(questionnaire);
    }

    // Delete a questionnaire
    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteQuestionnaire(@PathVariable(value="id") long id){
        // Check if logged in user is spine user
        if (authorizationService.getLoggedInUser().getRole() != User.Role.SPINE_USER || authorizationService.getLoggedInUser().getRole() != User.Role.SPINE_ADMIN) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not an employee.");
        }
        
        questionnaireRepo.deleteById(id);
    }

}
