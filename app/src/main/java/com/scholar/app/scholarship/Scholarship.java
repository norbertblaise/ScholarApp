package com.scholar.app.scholarship;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.util.Date;

@IgnoreExtraProperties
public class Scholarship implements Parcelable {

    private String scholarshipId;
    private String scholarshipTitle;
    private String description;
    private String degreeType;
    private String amountOffered;
    private String scholarshipStatus;
    private String studentId;
    private @ServerTimestamp
    Date timestamp;

    public Scholarship() {
        //required empty public constructor
    }

    public Scholarship(String scholarshipTitle, String description, String degreeType, String amountOffered, String scholarshipStatus, String studentId) {
        this.scholarshipTitle = scholarshipTitle;
        this.description = description;
        this.degreeType = degreeType;
        this.amountOffered = amountOffered;
        this.scholarshipStatus = scholarshipStatus;
        this.studentId = studentId;
//        this.timestamp = timestamp;
    }


    protected Scholarship(Parcel in) {
        scholarshipId = in.readString();
        scholarshipTitle = in.readString();
        description = in.readString();
        degreeType = in.readString();
        amountOffered = in.readString();
        scholarshipStatus = in.readString();
        studentId = in.readString();
    }

    public static final Creator<Scholarship> CREATOR = new Creator<Scholarship>() {
        @Override
        public Scholarship createFromParcel(Parcel in) {
            return new Scholarship(in);
        }

        @Override
        public Scholarship[] newArray(int size) {
            return new Scholarship[size];
        }
    };

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

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Date getTimestamp() { return timestamp; }

    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(scholarshipId);
        dest.writeString(scholarshipTitle);
        dest.writeString(description);
        dest.writeString(degreeType);
        dest.writeString(amountOffered);
        dest.writeString(scholarshipStatus);
        dest.writeString(studentId);
    }
}
