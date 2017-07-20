package com.androsol.instantremedy;

import java.util.ArrayList;

/**
 * Created by Dhruv on 17-07-2017.
 */

public class BodyPart {
    private String name;
    private String id;
    private ArrayList<Symptom> Symptoms;
    public BodyPart(String id, String name, ArrayList<Symptom> Symptoms){
        this.id = id;
        this.name = name;
        this.Symptoms = Symptoms;
    }

    public ArrayList<Symptom> getSymptoms() {
        return Symptoms;
    }

    public void setSymptoms(ArrayList<Symptom> Symptoms) {
        this.Symptoms = Symptoms;
    }

    public BodyPart(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
