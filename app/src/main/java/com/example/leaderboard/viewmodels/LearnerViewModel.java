package com.example.leaderboard.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.leaderboard.models.Learner;
import com.example.leaderboard.repositories.LearnerRepository;
import com.example.leaderboard.services.LearnerService;
import com.example.leaderboard.services.APINetworkService;

import java.util.List;

public class LearnerViewModel extends ViewModel {

    private LearnerRepository mLearnerRepository;


    public LearnerViewModel(){
        LearnerService learnerService = APINetworkService.getConnection().create(LearnerService.class);
        mLearnerRepository = new LearnerRepository(learnerService);
    }



    public LiveData<List<Learner>> getLearners() {

        MutableLiveData<List<Learner>> learnerList = new MutableLiveData<>();
        mLearnerRepository.loadLearnerData(learnerList);
        return learnerList;
    }
}
