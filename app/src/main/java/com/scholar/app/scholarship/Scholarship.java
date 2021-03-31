package com.scholar.app.scholarship;

import java.io.Serializable;

public class Scholarship implements Serializable {

    private String scholarshipId;
    private String scholarshipTitle;
    private String description;
    private String degreeType;
    private String amountOffered;
    private String scholarshipDemand;
    private String scholarshipStatus;

    public Scholarship() {
        //required empty public constructor
    }

    public Scholarship(String scholarshipId, String scholarshipTitle,String description, String degreeType, String amountOffered) {
        this.scholarshipId = scholarshipId;
        this.description = description;
        this.degreeType = degreeType;
        this.amountOffered = amountOffered;
    }

    public String getScholarshipId() {
        return scholarshipId;
    }

    public void setScholarshipId(String scholarshipId) {
        this.scholarshipId = scholarshipId;
    }

    public String getScholarshipTitle() {
        return scholarshipTitle;
    }

    public void setScholarshipTitle(String scholarshipTitle) {
        this.scholarshipTitle = scholarshipTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDegreeType() {
        return degreeType;
    }

    public void setDegreeType(String degreeType) {
        this.degreeType = degreeType;
    }

    public String getAmountOffered() {
        return amountOffered;
    }

    public void setAmountOffered(String amountOffered) {
        this.amountOffered = amountOffered;
    }

    public String getScholarshipStatus() {
        return scholarshipStatus;
    }

    public void setScholarshipStatus(String scholarshipStatus) {
        this.scholarshipStatus = scholarshipStatus;
    }

    public String getScholarshipDemand() {
        return scholarshipDemand;
    }

    public void setScholarshipDemand(String scholarshipDemand) {
        this.scholarshipDemand = scholarshipDemand;
    }
}
