package org.spine.iquestionapi.controller;

import org.spine.iquestionapi.model.Questionnaire;
import org.spine.iquestionapi.repository.QuestionnaireRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/questionnaire")
@ResponseStatus(HttpStatus.OK)
public class QuestionnaireController {

    @Autowired private QuestionnaireRepo questionnaireRepo;

    @GetMapping("/all")
    /**
     * Get all questionnaires
     * @return a list of all questionnaires
     */
    public Questionnaire[] getAllQuestionnaires(){
        return questionnaireRepo.findAll().toArray(new Questionnaire[0]);
    }

    @GetMapping("/{id}")
    @ResponseBody
    /**
     * Get a questionnaire by id
     * @param id the id of the questionnaire
     * @return the questionnaire
     */
    public Questionnaire getQuestionnaireById(@PathVariable(value="id") long id){
        return questionnaireRepo.findById(id).get();
    }

    @PutMapping("/")
    @ResponseBody
    /**
     * Create a questionnaire
     * @param questionnaire the questionnaire to be created
     * @return the created questionnaire
     */
    public Questionnaire createQuestionnaire(@RequestBody Questionnaire questionnaire){
        return questionnaireRepo.save(questionnaire);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    /**
     * Delete a questionnaire
     * @param id the id of the questionnaire to be deleted
     * @return the deleted questionnaire
     */
    public void deleteQuestionnaire(@PathVariable(value="id") long id){
        questionnaireRepo.deleteById(id);
    }

}
