package com.example.taskmanager2.controller.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

import com.example.taskmanager2.R;
import com.example.taskmanager2.controller.fragment.AllTasksFragment;
import com.example.taskmanager2.controller.fragment.AllUsersFragment;
import com.google.android.material.button.MaterialButton;

public class AdminActivity extends AppCompatActivity {
    public static final String IS_VISIBLE = "isVisible";
    private MaterialButton mButtonTaskList;
    private MaterialButton mButtonUserList;
    private Boolean mIsVisible=true;


    public static Intent newIntent(Context context){
       Intent intent=new Intent(context,AdminActivity.class);
       return intent;
   }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null){
            mIsVisible=savedInstanceState.getBoolean(IS_VISIBLE);
            if(!mIsVisible)
                goneViews();
        }
        setContentView(R.layout.activity_admin);

        findViews();
        setListeners();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_VISIBLE,mIsVisible);

    }

    private void startFragment(Fragment choicedFragment) {
        Log.d("TAG","AdminActivity startFragment  ");

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
                Log.d("TAG","AdminA userList Listener ");
                goneViews();
                startFragment(AllUsersFragment.newInstance());

            }
        });

        mButtonTaskList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG","AdminA userList Listener ");
                goneViews();
                startFragment(AllTasksFragment.newInstance());

            }
        });
    }

    private void goneViews() {
        mIsVisible=false;
        mButtonUserList.setVisibility(View.GONE);
        mButtonTaskList.setVisibility(View.GONE);
    }

    private void findViews() {
        mButtonTaskList=findViewById(R.id.btn_all_tasks_list);
        mButtonUserList=findViewById(R.id.btn_all_users_list);
    }

}