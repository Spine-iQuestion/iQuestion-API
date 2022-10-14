package org.spine.iquestionapi.model;

import java.util.ArrayList;

public class Entry implements Model {
    private int id;
    private Questionnaire questionnaire;
    private User caregiver;
    private ArrayList<Answer> answers;

    public Entry(Questionnaire questionnaire, User caregiver) {
        this.questionnaire = questionnaire;
        this.caregiver = caregiver;
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }

    @Override
    public String serialize() {
        // TODO Auto-generated method stub
        return null;
    }
}
