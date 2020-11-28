package com.example.taskmanager2.controller.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.taskmanager2.R;


public abstract class SingleFragmentActivity extends AppCompatActivity {

    /****************************** ON CREATE ***************************/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragmentManager.
                    beginTransaction().
                    add(R.id.fragment_container, getFragment()).
                    commit();
        }
    }

    /***************** GET FRAGMENT *****************/


    public abstract Fragment getFragment();
}

