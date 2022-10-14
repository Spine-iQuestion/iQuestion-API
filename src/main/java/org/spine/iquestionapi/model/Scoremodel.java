package org.spine.iquestionapi.model;

import java.util.HashMap;

public class Scoremodel implements Model{
    enum Result {
        PRESENT, PLAUSIBLE, ABSENT
    }

    private int id;
    private HashMap<Segment,Result> condition;
    private String result;

    public Scoremodel (HashMap<Segment,Result> condition, String result) {
        this.condition = condition;
        this.result = result;
    }

    @Override
    public String serialize() {
        // TODO Auto-generated method stub
        return null;
    }
}
