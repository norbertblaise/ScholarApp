package com.scholar.app.student;

public class Student {
    private String sid;
    private String name;
    private String dob;
    private String nationality;
    private String countryOfResidence;
    private String city;
    private String university;
    private String uniLocation;
    private String courseOfStudy;
    private String degree;
    private String startDate;
    private String expectedGradDate;

    public Student(){
        //required empty public constructor
    }

    public Student(String sid, String name, String dob, String nationality, String countryOfResidence,
                   String city, String university, String uniLocation, String courseOfStudy,
                   String degree, String startDate, String expectedGradDate) {
        this.sid = sid;
        this.name = name;
        this.dob = dob;
        this.nationality = nationality;
        this.countryOfResidence = countryOfResidence;
        this.city = city;
        this.university = university;
        this.uniLocation = uniLocation;
        this.courseOfStudy = courseOfStudy;
        this.degree = degree;
        this.startDate = startDate;
        this.expectedGradDate = expectedGradDate;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getCountryOfResidence() {
        return countryOfResidence;
    }

    public void setCountryOfResidence(String countryOfResidence) {
        this.countryOfResidence = countryOfResidence;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getUniLocation() {
        return uniLocation;
    }

    public void setUniLocation(String uniLocation) {
        this.uniLocation = uniLocation;
    }

    public String getCourseOfStudy() {
        return courseOfStudy;
    }

    public void setCourseOfStudy(String courseOfStudy) {
        this.courseOfStudy = courseOfStudy;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getExpectedGradDate() {
        return expectedGradDate;
    }

    public void setExpectedGradDate(String expectedGradDate) {
        this.expectedGradDate = expectedGradDate;
    }
}
