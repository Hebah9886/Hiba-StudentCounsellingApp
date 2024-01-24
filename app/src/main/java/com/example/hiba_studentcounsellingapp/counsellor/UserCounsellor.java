package com.example.hiba_studentcounsellingapp.counsellor;

import com.google.firebase.database.Exclude;

public class UserCounsellor {
    public String counsellorName;
    public String counsellorQualification;
    public String counsellorExperience;
    public String counsellorExpertise;

    public String mKey;

    public UserCounsellor() {
    }

    public UserCounsellor(String counsellorName, String counsellorQualification, String counsellorExperience, String counsellorExpertise) {
        this.counsellorName = counsellorName;
        this.counsellorQualification = counsellorQualification;
        this.counsellorExperience = counsellorExperience;
        this.counsellorExpertise = counsellorExpertise;
    }

    public String getCounsellorName() {
        return counsellorName;
    }

    public void setCounsellorName(String counsellorName) {
        this.counsellorName = counsellorName;
    }

    public String getCounsellorQualification() {
        return counsellorQualification;
    }

    public void setCounsellorQualification(String counsellorQualification) {
        this.counsellorQualification = counsellorQualification;
    }

    public String getCounsellorExperience() {
        return counsellorExperience;
    }

    public void setCounsellorExperience(String counsellorExperience) {
        this.counsellorExperience = counsellorExperience;
    }

    public String getCounsellorExpertise() {
        return counsellorExpertise;
    }

    public void setCounsellorExpertise(String counsellorExpertise) {
        this.counsellorExpertise = counsellorExpertise;
    }



    @Exclude
    public String getKey() {
        return mKey;
    }
    @Exclude
    public void setKey(String Key) {
        mKey = Key;
    }
}
