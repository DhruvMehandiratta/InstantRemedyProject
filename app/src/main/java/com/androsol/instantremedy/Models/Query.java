package com.androsol.instantremedy.Models;

/**
 * Created by Dhruv on 21-07-2017.
 */

public class Query {
    private String uniqueName;
    private String otherSymotoms;
    private String optionalText;
    private String bodypartId;
    private String bodypartName;

    public String getBodypartName() {
        return bodypartName;
    }

    public void setBodypartName(String bodypartName) {
        this.bodypartName = bodypartName;
    }

    public Query() {
    }

    public String getBodypartId() {
        return bodypartId;
    }

    public void setBodypartId(String bodypartId) {
        this.bodypartId = bodypartId;
    }

    public Query(String uniqueName, String otherSymotoms, String optionalText, String bodypartId, String bodypartName) {
        this.bodypartId = bodypartId;
        this.uniqueName = uniqueName;
        this.otherSymotoms = otherSymotoms;
        this.optionalText = optionalText;
        this.bodypartName = bodypartName;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }

    public String getOtherSymotoms() {
        return otherSymotoms;
    }

    public void setOtherSymotoms(String otherSymotoms) {
        this.otherSymotoms = otherSymotoms;
    }

    public String getOptionalText() {
        return optionalText;
    }

    public void setOptionalText(String optionalText) {
        this.optionalText = optionalText;
    }
}
