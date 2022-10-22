package org.spine.iquestionapi.controller;

import org.spine.iquestionapi.model.Questionnaire;
import org.spine.iquestionapi.repository.QuestionnaireRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/questionnaire")
public class QuestionnaireController {

    @Autowired private QuestionnaireRepo questionnaireRepo;

    // Get all questionnaires
    @GetMapping("/all")

    public Questionnaire[] getAllQuestionnaires(){
        return questionnaireRepo.findAll().toArray(new Questionnaire[0]);
    }

    // Get a questionnaire by id
    @GetMapping("/{id}")
    @ResponseBody
    public Questionnaire getQuestionnaireById(@PathVariable(value="id") long id){
        return questionnaireRepo.findById(id).get();
    }

    // Create a questionnaire
    @PutMapping("/")
    @ResponseBody
    public Questionnaire createQuestionnaire(@RequestBody Questionnaire questionnaire){
        return questionnaireRepo.save(questionnaire);
    }

    // Delete a questionnaire
    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteQuestionnaire(@PathVariable(value="id") long id){
        questionnaireRepo.deleteById(id);
    }

}
