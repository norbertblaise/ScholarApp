package com.scholar.app.student;

public class Student {
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    private String userId;
    private String studentProfileId;
    private String name;
    private String dob;
    private String gender;
    private String bio;
    private String nationality;
    private String countryOfResidence;
    private String city;
    private String university;
    private String uniCountry;
    private String courseOfStudy;
    private String degree;
    private String startDate;
    private String expectedGradDate;

    public Student() {
        //required empty public constructor
    }


    public Student(String name, String dob, String gender, String bio,
                   String nationality, String countryOfResidence, String city, String university,
                   String uniCountry, String courseOfStudy, String degree, String startDate,
                   String expectedGradDate) {
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.bio = bio;
        this.nationality = nationality;
        this.countryOfResidence = countryOfResidence;
        this.city = city;
        this.university = university;
        this.uniCountry = uniCountry;
        this.courseOfStudy = courseOfStudy;
        this.degree = degree;
        this.startDate = startDate;
        this.expectedGradDate = expectedGradDate;
    }

    public String getStudentProfileId() {
        return studentProfileId;
    }

    public void setStudentProfileId(String studentProfileId) {
        this.studentProfileId = studentProfileId;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getUniCountry() {
        return uniCountry;
    }

    public void setUniCountry(String uniCountry) {
        this.uniCountry = uniCountry;
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
