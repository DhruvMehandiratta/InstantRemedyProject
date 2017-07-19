package com.appuptechnologies.instantremedy;

/**
 * Created by Dhruv on 17-07-2017.
 */

public class BodyPart {
    public String name;
    public long id;
    public BodyPart(long id, String name){
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
