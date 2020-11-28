package com.example.taskmanager2.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.taskmanager2.controller.fragment.AllUsersFragment;

public class AllUsersActivity extends SingleFragmentActivity {
    /******************* NEW INTENT *********************/

    public static Intent newIntent(Context context) {

        Intent intent = new Intent(context, AllUsersActivity.class);
        return intent;

    }
    /***************** GET FRAGMENT *****************/

    @Override
    public Fragment getFragment() {
        return AllUsersFragment.newInstance();
    }
}