package com.example.taskmanager2.controller.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.taskmanager2.R;
import com.example.taskmanager2.controller.fragment.AllTasksFragment;
import com.example.taskmanager2.controller.fragment.AllUsersFragment;
import com.google.android.material.button.MaterialButton;

public class AdminActivity extends AppCompatActivity {
    private MaterialButton mButtonTaskList;
    private MaterialButton mButtonUserList;


    public static Intent newIntent(Context context){
       Intent intent=new Intent(context,AdminActivity.class);
       return intent;
   }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        findViews();
        setListeners();

       /// startFragment();
    }

    private void startFragment(Fragment choicedFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.admin_fragments_container);

        if (fragment == null) {
            fragmentManager.
                    beginTransaction().
                    add(R.id.admin_fragments_container,choicedFragment).
                    commit();
        }
    }

    private void setListeners() {
        mButtonUserList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(AllUsersFragment.newInstance());
            }
        });

        mButtonTaskList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(AllTasksFragment.newInstance());

            }
        });
    }

    private void findViews() {
        mButtonTaskList=findViewById(R.id.btn_all_tasks_list);
        mButtonUserList=findViewById(R.id.btn_all_users_list);
    }

}