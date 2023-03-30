package com.example.relo.Model;

public class RecomendRecycler {
String Review;
String Name;

    public RecomendRecycler() {
    }

    public RecomendRecycler(String review, String name) {
        Review = review;
        Name = name;
    }

    public String getReview() {
        return Review;
    }

    public void setReview(String review) {
        Review = review;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}


