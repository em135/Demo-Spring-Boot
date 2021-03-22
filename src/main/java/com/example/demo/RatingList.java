package com.example.demo;

public class RatingList {

    private long id;
    private Rating ratings;

    public RatingList() {
    }

    public RatingList(long id, Rating ratings) {
        this.id = id;
        this.ratings = ratings;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Rating getRatings() {
        return ratings;
    }

    public void setRatings(Rating ratings) {
        this.ratings = ratings;
    }

}
