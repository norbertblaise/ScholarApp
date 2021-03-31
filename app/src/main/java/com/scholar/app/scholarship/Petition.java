package com.scholar.app.scholarship;

public class Petition {
    private String petitionId;
    private String scholarshipId;
    private String studentId;

    public Petition(){
        //empty public constructor
    }

    public Petition(String scholarshipId, String studentId) {
        this.scholarshipId = scholarshipId;
        this.studentId = studentId;
    }

    public String getPetitionId() {
        return petitionId;
    }

    public void setPetitionId(String petitionId) {
        this.petitionId = petitionId;
    }

    public String getScholarshipId() {
        return scholarshipId;
    }

    public void setScholarshipId(String scholarshipId) {
        this.scholarshipId = scholarshipId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
