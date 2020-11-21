package com.example.taskmanager2.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.taskmanager2.R;
import com.example.taskmanager2.controller.fragment.SignInFragment;


public class LauncherActivity extends AppCompatActivity {
    /********************** NEW INTENT *************************/
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, LauncherActivity.class);
        return intent;

    }

    /*********************** ON CREATE *********************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            SignInFragment signInFragment = SignInFragment.newInstance();

            fragmentManager.
                    beginTransaction().
                    add(R.id.fragment_container,signInFragment)
                    .commit();

        }
    }
}