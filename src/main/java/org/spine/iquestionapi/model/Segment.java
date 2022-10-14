package org.spine.iquestionapi.model;

import java.util.ArrayList;

public class Segment implements Model {
    private int id;
    private String title;
    private String description;
    private ArrayList<Question> questions;

    public Segment(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    @Override
    public String serialize() {
        // TODO Auto-generated method stub
        return null;
    }
}
