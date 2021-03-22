package com.example.demo;

public class Rating {

    private long reviewer1;
    private long reviewer2;

    public Rating() {
    }

    public Rating(long reviewer1, long reviewer2) {
        this.reviewer1 = reviewer1;
        this.reviewer2 = reviewer2;
    }

    public long getReviewer1() {
        return reviewer1;
    }

    public void setReviewer1(long reviewer1) {
        this.reviewer1 = reviewer1;
    }

    public long getReviewer2() {
        return reviewer2;
    }

    public void setReviewer2(long reviewer2) {
        this.reviewer2 = reviewer2;
    }

}
