package com.example.leaderboard.models;

public class Learner {


    private String name;
    private String country;
    private String hours;
    private String badgeUrl;


    public String getName() {
        return name;
    }

    public String getBadgeUrl() {
        return badgeUrl;
    }

    public String generateDescription(){
        return String.format("%s learning hours, %s.", hours, country);
    }



}
