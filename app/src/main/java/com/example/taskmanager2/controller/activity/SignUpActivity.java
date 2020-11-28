package com.example.taskmanager2.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.taskmanager2.R;
import com.example.taskmanager2.controller.fragment.SignUpFragment;

public class SignUpActivity extends SingleFragmentActivity {
    /******************* NEW INTENT *********************/

    public static Intent newIntent(Context context) {
        Log.d("TAG","this is SignUp activity  ");


        Intent intent = new Intent(context, SignUpActivity.class);
        return intent;
    }

    /***************** GET FRAGMENT *****************/

    @Override
    public Fragment getFragment() {
        return SignUpFragment.newInstance();

    }
}