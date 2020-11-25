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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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
    private ArrayList<TaskListFragment> mTaskListFragments=new ArrayList<>();

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

    /****************** ON CREATE OPTION MENU ********************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list_pager_activity, menu);
        return true;
    }

    /**************** ON OPTION MENU SELECTED ****************/
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_logout:
                buildLogoutAlertDialog();
                return true;
            case R.id.menu_item_delete_all_tasks:
                //todo delete all user tasks
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void buildLogoutAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext())
                .setTitle("Are you sure to exit ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                      Intent intent=SignInActivity.newIntent(TaskListPagerActivity.this);
                      startActivity(intent);

                    }
                })
                .setNegativeButton("No", null);

        AlertDialog dialog = builder.create();
        dialog.show();
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
            mTaskListFragments. add(TaskListFragment.newInstance(mUsername,TaskSate.TODO));
            mTaskListFragments.add(TaskListFragment.newInstance(mUsername,TaskSate.DOING));
            mTaskListFragments.add(TaskListFragment.newInstance(mUsername,TaskSate.DONE));

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