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
import com.example.taskmanager2.controller.fragment.AdminFragment;
import com.example.taskmanager2.controller.fragment.AllTasksFragment;
import com.example.taskmanager2.controller.fragment.AllUsersFragment;
import com.google.android.material.button.MaterialButton;

public class AdminActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context){
       Intent intent=new Intent(context,AdminActivity.class);
       return intent;
   }


    @Override
    public Fragment getFragment() {
        return AdminFragment.newInstance();
    }
}