package com.example.leaderboard.models;

public class Skill {

    String name;
    String score;
    String country;
    String badgeUrl;

    public String getName() {
        return name;
    }

    public String getBadgeUrl() {
        return badgeUrl;
    }

    public String generateDescription(){
        return String.format("%s Skill IQ Score, %s.", score, country);
    }



}
