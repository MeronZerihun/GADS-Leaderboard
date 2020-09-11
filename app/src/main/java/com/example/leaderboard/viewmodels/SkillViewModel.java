package com.example.leaderboard.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.leaderboard.models.Skill;
import com.example.leaderboard.repositories.SkillRepository;
import com.example.leaderboard.services.APINetworkService;
import com.example.leaderboard.services.SkillService;

import java.util.List;

public class SkillViewModel extends ViewModel {


    private SkillRepository mSkillRepository;


    public SkillViewModel(){
        SkillService skillService = APINetworkService.getConnection().create(SkillService.class);
        mSkillRepository = new SkillRepository(skillService);
    }


    public LiveData<List<Skill>> getSkills() {

        MutableLiveData<List<Skill>> skillList = new MutableLiveData<List<Skill>>();
        mSkillRepository.loadSkillScores(skillList);
        return skillList;
    }
}
