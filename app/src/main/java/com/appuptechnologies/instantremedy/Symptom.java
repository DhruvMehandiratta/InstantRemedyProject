package com.appuptechnologies.instantremedy;

/**
 * Created by Dhruv on 17-07-2017.
 */

public class Symptom {
    public String name;
//    public String description;
//    public String remedy;
    public long id;

    public Symptom(long id, String name) {
        this.name = name;
        this.id = id;
    }

    public long getId() {

        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
