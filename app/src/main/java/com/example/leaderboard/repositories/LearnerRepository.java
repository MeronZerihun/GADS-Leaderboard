package com.example.leaderboard.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.leaderboard.models.Learner;
import com.example.leaderboard.services.LearnerService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LearnerRepository {

    private LearnerService mService;

    public LearnerRepository(LearnerService service) {
        this.mService = service;

    }

    public void loadLearnerData(final MutableLiveData<List<Learner>> learnerList){

        final Call<List<Learner>> learners = mService.getLearners();

        learners.enqueue(new Callback<List<Learner>>() {
            @Override
            public void onResponse(Call<List<Learner>> call, Response<List<Learner>> response) {

                learnerList.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Learner>> call, Throwable t) {

            }
        });
    }




}
