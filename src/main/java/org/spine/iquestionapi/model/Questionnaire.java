package org.spine.iquestionapi.model;

import java.util.ArrayList;

public class Questionnaire implements Model {

    private int id;
    private String name;
    private ArrayList segments;
    private Scoremodel scoremodel;


    @Override
    public String serialize() {
        // TODO Auto-generated method stub
        return null;
    }
}
