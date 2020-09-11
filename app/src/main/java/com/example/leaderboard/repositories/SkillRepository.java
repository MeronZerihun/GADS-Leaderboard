package com.example.leaderboard.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.leaderboard.models.Skill;
import com.example.leaderboard.services.SkillService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SkillRepository {

    SkillService mService;


    public SkillRepository(SkillService service) {
       this.mService = service;

    }

    public void loadSkillScores(final MutableLiveData<List<Skill>> skillList){


        Call<List<Skill>> scores = mService.getSkillScores();

        scores.enqueue(new Callback<List<Skill>>() {
            @Override
            public void onResponse(Call<List<Skill>> call, Response<List<Skill>> response) {

                skillList.setValue(response.body());

            }

            @Override
            public void onFailure(Call<List<Skill>> call, Throwable t) {

            }
        });
    }





}
