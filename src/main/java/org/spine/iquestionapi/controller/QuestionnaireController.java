package org.spine.iquestionapi.controller;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.spine.iquestionapi.dto.QuestionnaireDto;
import org.spine.iquestionapi.model.Questionnaire;
import org.spine.iquestionapi.model.User;
import org.spine.iquestionapi.repository.EntryRepo;
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
    @Autowired EntryRepo entryRepo;

    /**
     * Get all questionnaires
     * @return a list of all questionnaires
     */
    @GetMapping("/all")
    public QuestionnaireDto[] getAllQuestionnaires(){
        Questionnaire[] questionnaires = questionnaireRepo.findByEnabled(true).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "QUESTIONNAIRE_NOT_FOUND"));
        // create dto for each questionnaire
        Set<QuestionnaireDto> questionnaireDtos = new HashSet<QuestionnaireDto>();
        for (Questionnaire questionnaire : questionnaires) {
            QuestionnaireDto questionnaireDto = new QuestionnaireDto(entryRepo);
            questionnaireDtos.add(questionnaireDto.fromQuestionnaire(questionnaire));
        }

        return questionnaireDtos.toArray(new QuestionnaireDto[questionnaireDtos.size()]);
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
        // check if questionnaire exists
        questionnaireRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "QUESTIONNAIRE_NOT_FOUND"));

        // set questionnaire to disabled
        Questionnaire questionnaire = questionnaireRepo.findById(id).get();
        questionnaire.setEnabled(false);
        questionnaireRepo.save(questionnaire);
    }

}
