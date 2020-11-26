package com.example.taskmanager2.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.example.taskmanager2.controller.fragment.SignInFragment;


public class SignInActivity extends SingleFragmentActivity {
    /********************** NEW INTENT *************************/
    public static Intent newIntent(Context context) {

        Intent intent = new Intent(context, SignInActivity.class);
        return intent;

    }


    @Override
    public Fragment getFragment() {
        return SignInFragment.newInstance();
    }
}