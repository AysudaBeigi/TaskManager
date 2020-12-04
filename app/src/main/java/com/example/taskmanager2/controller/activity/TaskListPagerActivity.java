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
import android.util.Log;

import com.example.taskmanager2.R;
import com.example.taskmanager2.controller.fragment.TaskListFragment;
import com.example.taskmanager2.model.TaskState;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class TaskListPagerActivity extends AppCompatActivity {

    public static final String EXTRA_USERNAME = "extraUsername";
    public static final String BUNDEL_TASK_LIST_FRAGMENTS = "taskListFragments";
    private TabLayout mTabLayout;
    private ViewPager2 mViewPager2;
    private String mUsername;
    private ArrayList<TaskListFragment> mTaskListFragments = new ArrayList<>();

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
        if (savedInstanceState != null) {
            /*mTaskListFragments =
                    (ArrayList<TaskListFragment>) savedInstanceState
                            .getSerializable(BUNDEL_TASK_LIST_FRAGMENTS);
            */
        }

        findViews();
        initViews();

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(BUNDEL_TASK_LIST_FRAGMENTS, mTaskListFragments);
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

            mTaskListFragments.add(TaskListFragment.newInstance(mUsername, TaskState.TODO));
            mTaskListFragments.add(TaskListFragment.newInstance(mUsername, TaskState.DOING));
            mTaskListFragments.add(TaskListFragment.newInstance(mUsername, TaskState.DONE));

            return mTaskListFragments.get(position);
        }

    }

    /************************ SET TAB VIEW *******************************/
    private void setTabView() {

        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mTaskListFragments.get(position).updateList();
            }

        });

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d("TAG", "addOnTabSelectedListener");
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