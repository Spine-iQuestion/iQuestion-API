package org.spine.iquestionapi.controller;

import java.util.UUID;

import org.spine.iquestionapi.model.Questionnaire;
import org.spine.iquestionapi.model.User;
import org.spine.iquestionapi.repository.QuestionnaireRepo;
import org.spine.iquestionapi.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * The controller for the questionnaire
 */
@RestController
@RequestMapping("/questionnaire")
@ResponseStatus(HttpStatus.OK)
public class QuestionnaireController {

    @Autowired private QuestionnaireRepo questionnaireRepo;
    @Autowired
    private AuthorizationService authorizationService;

    /**
     * Get all questionnaires
     * @return a list of all questionnaires
     */
    @GetMapping("/all")
    public Questionnaire[] getAllQuestionnaires(){
        return questionnaireRepo.findAll().toArray(new Questionnaire[0]);
    }

    /**
     * Get a questionnaire by id
     * @param id the id of the questionnaire
     * @return the questionnaire
     */
    @GetMapping("/{id}")
    @ResponseBody
    public Questionnaire getQuestionnaireById(@PathVariable(value="id") UUID id){
        return questionnaireRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "QUESTIONNAIRE_NOT_FOUND"));
    }

    /**
     * Create a questionnaire
     * @param questionnaire the questionnaire to be created
     * @return the created questionnaire
     */
    @PutMapping("/")
    @ResponseBody
    public Questionnaire createQuestionnaire(@RequestBody Questionnaire questionnaire){
        User loggedInUser = authorizationService.getLoggedInUser();
        questionnaire.setAuthor(loggedInUser);
        questionnaire.setTimestamp(System.currentTimeMillis());
        return questionnaireRepo.save(questionnaire);
    }

    /**
     * Delete a questionnaire
     * @param id the id of the questionnaire to be deleted
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteQuestionnaire(@PathVariable(value="id") UUID id){
        questionnaireRepo.deleteById(id);
    }

}
