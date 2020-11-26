package com.example.taskmanager2.controller.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmanager2.R;
import com.example.taskmanager2.controller.activity.AllTasksActivity;
import com.example.taskmanager2.controller.activity.AllUsersActivity;
import com.google.android.material.button.MaterialButton;

public class AdminFragment extends Fragment {
    private MaterialButton mButtonTaskList;
    private MaterialButton mButtonUserList;



    public AdminFragment() {
        // Required empty public constructor
    }

    public static AdminFragment newInstance( ) {
        AdminFragment fragment = new AdminFragment();
        Bundle args = new Bundle();
          fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_admin, container, false);
        findViews(view);
        setListeners();
        return view;
    }
    private void findViews(View view) {
        mButtonTaskList=view.findViewById(R.id.btn_all_tasks_list);
        mButtonUserList=view.findViewById(R.id.btn_all_users_list);
    }



    private void setListeners() {
        mButtonUserList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= AllUsersActivity.newIntent(getActivity());
                startActivity(intent);

            }
        });

        mButtonTaskList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= AllTasksActivity.newIntent(getActivity());
                startActivity(intent);

            }
        });
    }

}