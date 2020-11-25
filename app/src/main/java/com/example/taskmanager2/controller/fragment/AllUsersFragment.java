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

import com.example.taskmanager2.R;
import com.example.taskmanager2.model.User;
import com.example.taskmanager2.repository.UserDBRepository;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class AllUsersFragment extends Fragment {
    private RecyclerView mAllUsersRecyclerView;
    private UserDBRepository mUserDBRepository;
    private AllUsersAdapter mAllUsersAdapter;

    public AllUsersFragment() {
        // Required empty public constructor
    }


    public static AllUsersFragment newInstance() {
        Log.d("TAG","all users fragment");
        AllUsersFragment fragment = new AllUsersFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_users, container, false);

        findView(view);

        initView();

        updateList();

        return view;
    }

    private void updateList() {

        mUserDBRepository = UserDBRepository.getInstance();
        List<User> users = mUserDBRepository.getUsers();

        if (users.size() != 0 && users != null) {

            if (mAllUsersAdapter != null) {

                mAllUsersAdapter.setUsers(users);
                mAllUsersAdapter.notifyDataSetChanged();
            } else {
                mAllUsersAdapter = new AllUsersAdapter(users);
                mAllUsersRecyclerView.setAdapter(mAllUsersAdapter);
            }
        }
    }

    private void initView() {
        mAllUsersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void findView(View view) {
        mAllUsersRecyclerView = view.findViewById(R.id.recycler_view_all_users);
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
                    mUserDBRepository.deleteUser(mUser);
                    updateList();
                }
            });


        }

        public void bindView(User user) {
            mUser = user;
            mTextViewUsername.setText(user.getUsername());
            SignUpDate.setText(user.getSignUpDate().toString());
            if (user.getTasks().size() != 0 && user.getTasks() != null)
                mTextViewCountUserTasks.setText(user.getTasks().size());
        }

        private void findViews(@NonNull View itemView) {
            mTextViewUsername = itemView.findViewById(R.id.text_view_task_title);
            SignUpDate = itemView.findViewById(R.id.text_view_sign_up_date);
            mTextViewCountUserTasks = itemView.findViewById(R.id.text_view_count__user_tasks);
            mImageViewDeleteUser = itemView.findViewById(R.id.img_view_delete_user);
        }

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