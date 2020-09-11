package com.example.leaderboard;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.leaderboard.viewmodels.GoogleFormViewModel;



public class ProjectSubmissionActivity extends AppCompatActivity {


    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText githubLink;
    private GoogleFormViewModel formViewModel;
    private ProgressDialog progressDialog;

    View mView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_submission_form);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Submitting...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);

        firstName = findViewById(R.id.first_name_edit_text);
        lastName = findViewById(R.id.last_name_edit_text);
        email = findViewById(R.id.email_edit_text);
        githubLink = findViewById(R.id.link_edit_text);


        formViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(GoogleFormViewModel.class);


    }

    public void submitForm(final View formView) {
        mView = getLayoutInflater().inflate(R.layout.check_action_dialog, null);
        final AlertDialog checkDialog = check_action_dialog(mView);

        Button yesButton = (Button) mView.findViewById(R.id.yes_button);
        ImageButton cancelBtn = mView.findViewById(R.id.cancel_button);



        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDialog.dismiss();

            }
        });

        if(!validateFields()) {
            hideKeyboard();
            checkDialog.show();
            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkDialog.dismiss();
                    progressDialog.show();

                    String firstNameText = firstName.getText().toString().trim();
                    String lastNameText = lastName.getText().toString().trim();
                    String emailText = email.getText().toString().trim();
                    String githubLinkText = githubLink.getText().toString().trim();

                    formViewModel.submitForm(firstNameText, lastNameText, emailText, githubLinkText).observe( (LifecycleOwner) view.getContext(), new Observer<String>() {
                        @Override
                        public void onChanged(String s) {


                            showMessage(s, formView);
                        }
                    });

                }
            });
        }
        else {
            Toast.makeText(ProjectSubmissionActivity.this,
                   "Make sure all fields are filled correctly!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void showMessage(String s, View view) {

        View popupView = getLayoutInflater().inflate(R.layout.confirmation_dialog, null);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;


        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

        TextView message = popupView.findViewById(R.id.message_details);
        ImageView messageImage = popupView.findViewById(R.id.message_image);
        if(s.toLowerCase().equals("submission successful")){
            messageImage.setImageResource(R.drawable.ic_successful);
        }
        else{
            messageImage.setImageResource(R.drawable.ic_not_successful);
        }
        message.setText(s);
        progressDialog.dismiss();
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        clearFields();

    }

    private void clearFields() {
        firstName.setText("");
        lastName.setText("");
        email.setText("");
        githubLink.setText("");
    }

    private AlertDialog check_action_dialog(View view){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ProjectSubmissionActivity.this);
        mBuilder.setView(view);
        AlertDialog checkDialog = mBuilder.create();

        return checkDialog;

    }


    private boolean validateFields() {
        if (TextUtils.isEmpty(firstName.getText().toString())
                || TextUtils.isEmpty(lastName.getText().toString())
                || TextUtils.isEmpty(email.getText().toString())
                || TextUtils.isEmpty(githubLink.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(githubLink.getWindowToken(), 0);
        }
    }

