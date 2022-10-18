package org.spine.iquestionapi.controller;

import org.spine.iquestionapi.model.Questionnaire;
import org.spine.iquestionapi.repository.QuestionnaireRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Questionnaire getQuestionnaireById(@PathVariable(value="id") long id){
        return questionnaireRepo.findById(id).get();
    }

    // Create a questionnaire
    @PutMapping("/")
    public Questionnaire createQuestionnaire(Questionnaire questionnaire){
        return questionnaireRepo.save(questionnaire);
    }

    // Delete a questionnaire
    @DeleteMapping("/{id}")
    public void deleteQuestionnaire(@PathVariable(value="id") long id){
        questionnaireRepo.deleteById(id);
    }

}
