package com.rafi.attendecetaker;

import android.graphics.Bitmap;

public class FeedbackRetrives {
    String name;
    String occupation;
    String feedback;
    Bitmap bitmap;

    public FeedbackRetrives(String name, String feedback, Bitmap bitmap, String occupation) {
        this.name = name;
        this.feedback = feedback;
        this.bitmap = bitmap;
        this.occupation = occupation;
    }

    public String getName() {
        return name;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}

