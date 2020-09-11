package com.example.leaderboard.repositories;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.leaderboard.services.FormSubmissionService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GoogleFormRepository {

    private FormSubmissionService mService;

    public GoogleFormRepository(FormSubmissionService service) {
        this.mService = service;
    }



    public LiveData<String> submitFormData(final String firstName, String lastName, String email, String githubLink) {

        final MutableLiveData<String> formResponse = new MutableLiveData<String>();
        mService.submitForm(firstName, lastName, email, githubLink).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    Log.d("Repository", "Response:: " + response.body());
                    formResponse.setValue("Submission Successful");
                }
                else {
                    formResponse.setValue("Submission Unsuccessful");

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                formResponse.setValue("Submission Unsuccessful");
            }
        });

        return formResponse;
    }
}
