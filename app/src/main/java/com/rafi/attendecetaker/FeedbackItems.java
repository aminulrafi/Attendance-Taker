package com.rafi.attendecetaker;

public class FeedbackItems {
    String email;
    String occupation;
    String feedback;
    String status;

    public FeedbackItems(String email, String occupation, String feedback, String status) {
        this.email = email;
        this.occupation = occupation;
        this.feedback = feedback;
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
