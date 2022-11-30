package org.spine.iquestionapi.controller;

import org.spine.iquestionapi.model.Questionnaire;
import org.spine.iquestionapi.repository.QuestionnaireRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

/**
 * The controller for the questionnaire
 */
@RestController
@RequestMapping("/questionnaire")
@ResponseStatus(HttpStatus.OK)
public class QuestionnaireController {

    @Autowired private QuestionnaireRepo questionnaireRepo;

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
        return questionnaireRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The questionnaire was not found"));
    }

    /**
     * Create a questionnaire
     * @param questionnaire the questionnaire to be created
     * @return the created questionnaire
     */
    @PutMapping("/")
    @ResponseBody
    public Questionnaire createQuestionnaire(@RequestBody Questionnaire questionnaire){
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
