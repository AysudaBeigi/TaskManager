package com.example.taskmanager2.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.taskmanager2.controller.fragment.AllTasksFragment;

public class AllTasksActivity extends SingleFragmentActivity {
    /******************* NEW INTENT *********************/

    public static Intent newIntent(Context context) {

        Intent intent = new Intent(context, AllTasksActivity.class);
        return intent;

    }
    /***************** GET FRAGMENT *****************/

    @Override
    public Fragment getFragment() {
        return AllTasksFragment.newInstance();
    }
}