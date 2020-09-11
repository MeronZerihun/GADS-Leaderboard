package com.example.leaderboard.services;

import com.example.leaderboard.models.Learner;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LearnerService {
    @GET("api/hours")
    Call<List<Learner>> getLearners();
}
