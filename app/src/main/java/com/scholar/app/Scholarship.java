package com.scholar.app;

public class Scholarship {

    private String scholarshipId;
    private String description;
    private String degreeType;
    private String amountOffered;

    public Scholarship(){
        //required empty public constructor
    }

    public Scholarship(String scholarshipId, String description, String degreeType, String amountOffered) {
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
}
