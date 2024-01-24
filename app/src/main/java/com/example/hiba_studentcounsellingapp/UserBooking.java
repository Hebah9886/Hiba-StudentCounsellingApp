package com.example.hiba_studentcounsellingapp;

import com.google.firebase.database.Exclude;

public class UserBooking {
    public String bookingStudentName;
    public String bookingStudentContact;
    public String bookingCounsellorName;
    public String bookingCounsellorQualification;
    public String bookingCounsellorExperience;
    public String bookingCounsellorExpertise;
    public String bookingDate;
    public String bookingTime;
    public String mKey;

    public UserBooking() {
    }

    public UserBooking(String bookingStudentName, String bookingStudentContact, String bookingCounsellorName, String bookingCounsellorQualification, String bookingCounsellorExperience, String bookingCounsellorExpertise, String bookingDate, String bookingTime) {
        this.bookingStudentName = bookingStudentName;
        this.bookingStudentContact = bookingStudentContact;
        this.bookingCounsellorName = bookingCounsellorName;
        this.bookingCounsellorQualification = bookingCounsellorQualification;
        this.bookingCounsellorExperience = bookingCounsellorExperience;
        this.bookingCounsellorExpertise = bookingCounsellorExpertise;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
    }

    public String getBookingStudentName() {
        return bookingStudentName;
    }

    public void setBookingStudentName(String bookingStudentName) {
        this.bookingStudentName = bookingStudentName;
    }

    public String getBookingStudentContact() {
        return bookingStudentContact;
    }

    public void setBookingStudentContact(String bookingStudentContact) {
        this.bookingStudentContact = bookingStudentContact;
    }

    public String getBookingCounsellorName() {
        return bookingCounsellorName;
    }

    public void setBookingCounsellorName(String bookingCounsellorName) {
        this.bookingCounsellorName = bookingCounsellorName;
    }

    public String getBookingCounsellorQualification() {
        return bookingCounsellorQualification;
    }

    public void setBookingCounsellorQualification(String bookingCounsellorQualification) {
        this.bookingCounsellorQualification = bookingCounsellorQualification;
    }

    public String getBookingCounsellorExperience() {
        return bookingCounsellorExperience;
    }

    public void setBookingCounsellorExperience(String bookingCounsellorExperience) {
        this.bookingCounsellorExperience = bookingCounsellorExperience;
    }

    public String getBookingCounsellorExpertise() {
        return bookingCounsellorExpertise;
    }

    public void setBookingCounsellorExpertise(String bookingCounsellorExpertise) {
        this.bookingCounsellorExpertise = bookingCounsellorExpertise;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
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
