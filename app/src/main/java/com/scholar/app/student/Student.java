package com.scholar.app.student;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Student  implements Parcelable {



    private String studentId;
    private String name;
    private String dob;
    private String gender;
    private String bio;
    private String country;
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


    public Student(String studentId, String name, String dob, String gender, String bio, String country, String city, String university, String uniCountry, String courseOfStudy, String degree, String startDate, String expectedGradDate) {
        this.studentId = studentId;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.bio = bio;
        this.country = country;
        this.city = city;
        this.university = university;
        this.uniCountry = uniCountry;
        this.courseOfStudy = courseOfStudy;
        this.degree = degree;
        this.startDate = startDate;
        this.expectedGradDate = expectedGradDate;
    }

    protected Student(Parcel in) {
        studentId = in.readString();
        name = in.readString();
        dob = in.readString();
        gender = in.readString();
        bio = in.readString();
        country = in.readString();
        city = in.readString();
        university = in.readString();
        uniCountry = in.readString();
        courseOfStudy = in.readString();
        degree = in.readString();
        startDate = in.readString();
        expectedGradDate = in.readString();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
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


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(studentId);
        dest.writeString(name);
        dest.writeString(dob);
        dest.writeString(gender);
        dest.writeString(bio);
        dest.writeString(country);
        dest.writeString(city);
        dest.writeString(university);
        dest.writeString(uniCountry);
        dest.writeString(courseOfStudy);
        dest.writeString(degree);
        dest.writeString(startDate);
        dest.writeString(expectedGradDate);
    }
}
