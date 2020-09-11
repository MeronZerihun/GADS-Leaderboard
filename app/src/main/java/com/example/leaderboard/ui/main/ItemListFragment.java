package com.example.leaderboard.ui.main;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.leaderboard.adapters.ItemAdapter;
import com.example.leaderboard.R;
import com.example.leaderboard.models.Learner;
import com.example.leaderboard.models.Skill;
import com.example.leaderboard.viewmodels.LearnerViewModel;
import com.example.leaderboard.viewmodels.SkillViewModel;

import java.util.List;


public class ItemListFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private LearnerViewModel learnerViewModel;
    private SkillViewModel skillViewModel;

    private int mIndex;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private SwipeRefreshLayout refreshLayout;



    public static ItemListFragment newInstance(int index) {

        ItemListFragment fragment = new ItemListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        learnerViewModel = new ViewModelProvider(this).get(LearnerViewModel.class);
        skillViewModel = new ViewModelProvider(this).get(SkillViewModel.class);

        mIndex = 1;
        if (getArguments() != null) {
            mIndex = getArguments().getInt(ARG_SECTION_NUMBER);
        }

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);

        mProgressBar = view.findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);

        mRecyclerView = view.findViewById(R.id.item_list);
        refreshLayout = view.findViewById(R.id.refreshLayout);



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                displayView(view);
            }
        });


        displayView(view);



        Log.d("onCreateView", "onCreateView message " + mIndex);

        return view;
    }

    private void displayView(View view){

        if(!isNetworkAvailable(view)){
            showNoInternetAlertDialog(view);
        }
        if(isNetworkAvailable(view)) {
            showContent(view);
        }

    }

    public AlertDialog constructNoInternetAlert(View view){
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(view.getContext(), R.style.CustomAlertDialog);
        ViewGroup viewGroup = view.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.no_internet_dialog, viewGroup, false);
        alertBuilder.setView(dialogView);

        final AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        return alertDialog;

    }

    private void showNoInternetAlertDialog(final View view) {

        final AlertDialog alertDialog = constructNoInternetAlert(view);

        final Button retryButton =  alertDialog.getWindow().findViewById(R.id.retry_button);
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                final Handler handler = new Handler();
                if (!isNetworkAvailable(view)) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            alertDialog.show();
                        }
                    }, 100);
                } else {
                    alertDialog.dismiss();
                    showContent(v);
                }
            }
        });
    }

    private void showSkills(View v) {
        skillViewModel.getSkills().observe((LifecycleOwner) v.getContext(), new Observer<List<Skill>>() {
            @Override
            public void onChanged(List<Skill> skillScores) {
                ItemAdapter<Skill> skillItemAdapter = new ItemAdapter<>(getContext(), skillScores);
                mRecyclerView.setAdapter(skillItemAdapter);
                mProgressBar.setVisibility(View.GONE);
                refreshLayout.setRefreshing(false);
            }
        });

    }

    private void showLearners(View v) {
        learnerViewModel.getLearners().observe((LifecycleOwner) v.getContext(), new Observer<List<Learner>>() {
            @Override
            public void onChanged(List<Learner> learners) {
                ItemAdapter<Learner> learnerItemAdapter = new ItemAdapter<>(getContext(), learners);
                mRecyclerView.setAdapter(learnerItemAdapter);
                mProgressBar.setVisibility(View.GONE);
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void showContent(View v) {
        if (mIndex == 1) {
            refreshLayout.setRefreshing(true);
            showLearners(v);
        } else if (mIndex == 2) {
            refreshLayout.setRefreshing(true);
            showSkills(v);
        }
    }

    public boolean isNetworkAvailable(View view) {

        ConnectivityManager connectivityManager
                = (ConnectivityManager) view.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}