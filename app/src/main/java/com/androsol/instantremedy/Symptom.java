package com.androsol.instantremedy;

/**
 * Created by Dhruv on 17-07-2017.
 */

public class Symptom {
    private String name;
    private String otherSymps;

    public String getOtherSymps() {
        return otherSymps;
    }

    public void setOtherSymps(String otherSymps) {
        this.otherSymps = otherSymps;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemedy() {
        return remedy;
    }

    public void setRemedy(String remedy) {
        this.remedy = remedy;
    }

    private String description;
    private String remedy;
    private String id;

    public Symptom(String id, String name, String otherSymps, String description, String remedy) {
        this.name = name;
        this.id = id;
        this.otherSymps = otherSymps;
        this.description = description;
        this.remedy = remedy;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Symptom() {

    }
}