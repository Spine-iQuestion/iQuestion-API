package org.spine.iquestionapi.model;

public class Question implements Model {
    enum Type {
        OPEN, CLOSED
    }

    private int id;
    private String label;
    private Type type;

    public Question(String label, Type type) {
        this.label = label;
        this.type = type;
    }

    @Override
    public String serialize() {
        // TODO Auto-generated method stub
        return null;
    }
}
