package com.example.taskmanager2.controller.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.taskmanager2.R;
import com.example.taskmanager2.controller.fragment.TaskListFragment;
import com.example.taskmanager2.model.Task;
import com.example.taskmanager2.model.TaskState;
import com.example.taskmanager2.repository.TaskDBRepository;
import com.example.taskmanager2.repository.UserDBRepository;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class TaskListPagerActivity extends AppCompatActivity {

    public static final String EXTRA_USERNAME = "extraUsername";
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
           /* mTaskListFragments=new ArrayList<>();

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
            TaskListFragment taskListFragment= TaskListFragment.newInstance(mUsername, state);
            mTaskListFragments.add(taskListFragment);
          */
            mTaskListFragments.add(TaskListFragment.newInstance(mUsername, TaskState.TODO));
            mTaskListFragments.add(TaskListFragment.newInstance(mUsername, TaskState.DOING));
            mTaskListFragments.add(TaskListFragment.newInstance(mUsername, TaskState.DONE));

            return mTaskListFragments.get(position);
        }

    }

    /************************ SET TAB VIEW *******************************/
    private void setTabView() {
       /* TabLayoutMediator tabLayoutMediator = new TabLayoutMediator
                (mTabLayout, mViewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        switch (position) {
                            case 0: {
                                tab.setText("TODO");
                                break;
                            }
                            case 1: {
                                tab.setText("DOING");
                                break;
                            }
                            case 2: {
                                tab.setText("DONE");
                                break;
                            }
                        }
                    }
                });

        tabLayoutMediator.attach();

        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
               mTaskListFragments.get(position).updateList();
            }

        });*/

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