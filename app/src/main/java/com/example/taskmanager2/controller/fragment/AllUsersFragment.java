package com.example.taskmanager2.controller.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.taskmanager2.R;
import com.example.taskmanager2.model.Task;
import com.example.taskmanager2.model.TaskState;
import com.example.taskmanager2.model.User;
import com.example.taskmanager2.repository.TaskDBRepository;
import com.example.taskmanager2.repository.UserDBRepository;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AllUsersFragment extends Fragment {
    private RecyclerView mAllUsersRecyclerView;
    private UserDBRepository mUserDBRepository;
    private TaskDBRepository mTaskDBRepository;
    private AllUsersAdapter mAllUsersAdapter;
    private LinearLayout mLinearLayout;

    public AllUsersFragment() {
        // Required empty public constructor
    }


    public static AllUsersFragment newInstance() {
        Log.d("TAG", "all users fragment");
        AllUsersFragment fragment = new AllUsersFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskDBRepository = TaskDBRepository.getInstance(getActivity());

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("TAG", "AllUsersFrgament===onCreateView");
        View view = inflater.inflate(R.layout.fragment_all_users, container, false);

        findView(view);

        initView();

        updateList();

        return view;
    }

    private void updateList() {

        mUserDBRepository = UserDBRepository.getInstance(getActivity());
        List<User> users = mUserDBRepository.getUsers();

        if (mAllUsersAdapter != null) {

            mAllUsersAdapter.setUsers(users);
            mAllUsersAdapter.notifyDataSetChanged();
        } else {
            mAllUsersAdapter = new AllUsersAdapter(users);
            mAllUsersRecyclerView.setAdapter(mAllUsersAdapter);
        }
    }

    private void findView(View view) {
        mAllUsersRecyclerView = view.findViewById(R.id.recycler_view_all_users);
        mLinearLayout = view.findViewById(R.id.linear_layout_all_users);
    }

    private void initView() {
        mAllUsersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    public class AllUsersAdapter extends RecyclerView.Adapter<AllUsersViewHolder> {
        private List<User> mUsers;

        public List<User> getUsers() {
            return mUsers;
        }

        public void setUsers(List<User> users) {
            notifyDataSetChanged();
            mUsers = users;
        }

        public AllUsersAdapter(List<User> users) {
            mUsers = users;
        }

        @NonNull
        @Override
        public AllUsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View itemView = inflater.inflate(R.layout.user_item_view, parent, false);
            return new AllUsersViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull AllUsersViewHolder holder, int position) {
            holder.bindView(mUsers.get(position));

        }

        @Override
        public int getItemCount() {
            return mUsers.size();
        }
    }


    public class AllUsersViewHolder extends RecyclerView.ViewHolder {
        private MaterialTextView mTextViewUsername;
        private MaterialTextView SignUpDate;
        private MaterialTextView mTextViewCountUserTasks;
        private ShapeableImageView mImageViewDeleteUser;
        private User mUser;

        public AllUsersViewHolder(@NonNull View itemView) {
            super(itemView);

            findViews(itemView);
            mImageViewDeleteUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteUserTasks();
                    mUserDBRepository.deleteUser(mUser);

                    updateList();
                }
            });


        }

        private void deleteUserTasks() {
            List<Task> tasks = mUserDBRepository.getUserAllTasks(mUser.getUsername());
            if (tasks != null) {
                for (int i = 0; i < tasks.size(); i++) {
                    mTaskDBRepository.deleteTask(tasks.get(i));
                }
            }
        }

        public void bindView(User user) {
            mUser = user;
            if (user != null) {

                mTextViewUsername.setText(user.getUsername());
                SignUpDate.setText("SignUp date :" + getStringFormatDate(user.getSignUpDate()));
                List<Task> userAllTasks = mUserDBRepository.getUserAllTasks(user.getUsername());
                if (userAllTasks != null) {
                    Log.d("TAG", "AllUsersF binView user tasks is not null");
                    mTextViewCountUserTasks.
                            setText( "number of tasks : " + userAllTasks.size());
                    Log.d("TAG",String.valueOf(userAllTasks.size()));

                }
            } else {
                generateSnackbar(mLinearLayout, R.string.snackbar_not_exits_user);
                Toast.makeText(getActivity(), "there is no user exits", Toast.LENGTH_SHORT).show();

            }
        }

        private void findViews(@NonNull View itemView) {
            mTextViewUsername = itemView.findViewById(R.id.text_view_task_title);
            SignUpDate = itemView.findViewById(R.id.text_view_sign_up_date);
            mTextViewCountUserTasks = itemView.findViewById(R.id.text_view_count__user_tasks);
            mImageViewDeleteUser = itemView.findViewById(R.id.img_view_delete_user);
        }

    }


    /************************* GET STRING FORMAT DATE ******************/
    private String getStringFormatDate(Date date) {
        return new SimpleDateFormat("yyy/MM/dd  "+"HH:mm:ss" ).format(date);
    }


    /******************** GENERATE SNACK BAR *********************/
    private void generateSnackbar(View view, int stringId) {
        Snackbar snackbar = Snackbar
                .make(view,
                        stringId, Snackbar.LENGTH_LONG);
        snackbar.show();
    }


    @Override
    public void onPause() {

        super.onPause();
        updateList();
    }

    @Override
    public void onResume() {

        super.onResume();
        updateList();
    }

}