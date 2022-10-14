package org.spine.iquestionapi.model;

public class Answer implements Model {
    public int id;
    public Question question;
    public String answer;

    public Answer(Question question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    @Override
    public String serialize() {
        // TODO Auto-generated method stub
        return null;
    }
}
