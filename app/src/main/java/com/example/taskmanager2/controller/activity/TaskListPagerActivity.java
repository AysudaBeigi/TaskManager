package com.example.taskmanager2.controller.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.taskmanager2.R;
import com.example.taskmanager2.controller.fragment.TaskListFragment;
import com.example.taskmanager2.model.TaskSate;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class TaskListPagerActivity extends AppCompatActivity {

    public static final String EXTRA_USERNAME = "extraUsername";
    private TabLayout mTabLayout;
    private ViewPager2 mViewPager2;
    private String mUsername;

    /******************* NEW INTENT *********************/

    public static Intent newIntent(Context context, String username) {
        Intent intent = new Intent(context, TaskListPagerActivity.class);
        intent.putExtra(EXTRA_USERNAME, username);

        return intent;
    }

    /************************ ON CREATE ******************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list_pager);
        mUsername = getIntent().getStringExtra(EXTRA_USERNAME);

        findViews();
        initViews();

    }
    /********************* FIND VIEWS *********************/
    private void findViews() {
        mTabLayout = findViewById(R.id.tab_layout_task_list);
        mViewPager2 = findViewById(R.id.view_pager2_task_list);
    }


    /********************** INIT VIEWS **************************/
    private void initViews() {
        TaskPagerAdapter adapter = new TaskPagerAdapter(this);
        mViewPager2.setAdapter(adapter);
        setTabView();
    }

    /************************** TASK PAGER ADAPTER ******************/

    public class TaskPagerAdapter extends FragmentStateAdapter {

        public TaskPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @Override
        public int getItemCount() {
            return 3;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            TaskSate state = TaskSate.DONE;
            switch (position) {
                case 0:
                    state = TaskSate.TODO;
                    break;
                case 1:
                    state = TaskSate.DOING;
                    break;
                case 2:
                    state = TaskSate.DONE;
                    break;
            }
            return TaskListFragment.newInstance(mUsername, state);
        }

    }

    /************************ SET TAB VIEW *******************************/
    private void setTabView() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}