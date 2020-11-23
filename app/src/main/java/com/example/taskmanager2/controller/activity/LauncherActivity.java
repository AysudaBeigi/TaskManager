package com.example.taskmanager2.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.taskmanager2.R;
import com.example.taskmanager2.controller.fragment.SignInFragment;


public class LauncherActivity extends SingleFragmentActivity {
    /********************** NEW INTENT *************************/
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, LauncherActivity.class);
        return intent;

    }


    @Override
    public Fragment getFragment() {
        return SignInFragment.newInstance();
    }
}