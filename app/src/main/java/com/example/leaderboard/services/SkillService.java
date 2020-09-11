package com.example.leaderboard.services;

import com.example.leaderboard.models.Skill;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SkillService {
    @GET("api/skilliq")
    Call<List<Skill>> getSkillScores();

}
