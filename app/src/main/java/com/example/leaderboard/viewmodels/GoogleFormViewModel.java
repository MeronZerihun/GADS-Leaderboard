package com.example.leaderboard.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.leaderboard.repositories.GoogleFormRepository;
import com.example.leaderboard.services.FormSubmissionService;
import com.example.leaderboard.services.GoogleFormNetworkService;

import java.util.List;

public class GoogleFormViewModel extends ViewModel {


    private GoogleFormRepository mFormRepository;

    public GoogleFormViewModel() {

        FormSubmissionService service = GoogleFormNetworkService.getConnection().create(FormSubmissionService.class);
        mFormRepository = new GoogleFormRepository(service);
    }

    public LiveData<String> submitForm(String firstName, String lastName, String email, String link) {

        return  mFormRepository.submitFormData(firstName,lastName, email, link);

    }
}
