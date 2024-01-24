package com.example.hiba_studentcounsellingapp;

import com.google.firebase.database.Exclude;

public class UserFeedback {
    public String feedCounsellor;
    public String feedName;
    public String feedCount;
    public String feedReview;
    public String mKey;

    public UserFeedback() {
    }

    public UserFeedback(String feedCounsellor, String feedName, String feedCount, String feedReview) {
        this.feedCounsellor = feedCounsellor;
        this.feedName = feedName;
        this.feedCount = feedCount;
        this.feedReview = feedReview;
    }

    public String getFeedCounsellor() {
        return feedCounsellor;
    }

    public void setFeedCounsellor(String feedCounsellor) {
        this.feedCounsellor = feedCounsellor;
    }

    public String getFeedName() {
        return feedName;
    }

    public void setFeedName(String feedName) {
        this.feedName = feedName;
    }

    public String getFeedCount() {
        return feedCount;
    }

    public void setFeedCount(String feedCount) {
        this.feedCount = feedCount;
    }

    public String getFeedReview() {
        return feedReview;
    }

    public void setFeedReview(String feedReview) {
        this.feedReview = feedReview;
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
