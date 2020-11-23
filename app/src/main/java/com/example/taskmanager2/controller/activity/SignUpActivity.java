package com.example.taskmanager2.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.taskmanager2.R;
import com.example.taskmanager2.controller.fragment.SignUpFragment;

public class SignUpActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context) {

         Intent intent = new Intent(context, SignUpActivity.class);
        return intent;
    }


    @Override
    public Fragment getFragment() {
        return SignUpFragment.newInstance();

    }
}