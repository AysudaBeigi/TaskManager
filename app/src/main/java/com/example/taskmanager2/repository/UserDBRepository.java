package com.example.taskmanager2.repository;

import android.util.Log;

import com.example.taskmanager2.model.Task;
import com.example.taskmanager2.model.TaskSate;
import com.example.taskmanager2.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDBRepository implements IRepository {

    private List<User> mUsers;
    private List<Task> mAllTasks;
    private TaskDBRepository mTaskDBRepository;


    private static UserDBRepository sInstance;


    private UserDBRepository() {
        Log.d("TAG","new  uer repository and user array list ");

        mUsers = new ArrayList<>();
    }

    public static UserDBRepository getInstance() {

        if (sInstance == null) {
        Log.d("TAG","sInstance is null ");
            sInstance = new UserDBRepository();
            return sInstance;
        }
        Log.d("TAG","sInstance is not null ");

        return sInstance;
    }

    public List<User> getUsers() {
        return mUsers;
    }

    public User getUser(String username) {
        for (int i = 0; i < mUsers.size(); i++) {
            if (mUsers.get(i).getUsername().equals(username))
                return mUsers.get(i);
        }
        return null;
    }


    public User getUser(UUID uuid) {
        for (int i = 0; i < mUsers.size(); i++) {
            if (mUsers.get(i).getUUID().equals(uuid))
                return mUsers.get(i);
        }
        return null;
    }


    public void insertUser(User user) {
        Log.d("TAG", "insert user  ");

        mUsers.add(user);

    }

    public void deleteUser(User user) {
        mUsers.remove(user);

    }


    public void updateUser(User user) {


        for (int i = 0; i < mUsers.size(); i++) {
            if (mUsers.get(i).getUUID().equals(user.getUUID())) {
                mUsers.get(i).setPassword(user.getPassword());
                mUsers.get(i).setUsername(user.getUsername());

            }
        }


    }

    public List<Task> getTasks(String username, TaskSate state) {
        mTaskDBRepository=TaskDBRepository.getInstance();
        mAllTasks=mTaskDBRepository.getTasks();

        List<Task> userTasks = new ArrayList<>();

        for (int i = 0; i < mAllTasks.size(); i++) {
            if (mAllTasks.get(i).getUsername().equals(username)) {
                if (mAllTasks.get(i).getSate().toString().equals(state.toString()))
                    userTasks.add(mAllTasks.get(i));
            }
        }
        return userTasks;
    }




    public boolean isUsernameTaken(String username) {
        if (getUser(username) != null)
            return true;
        return false;
    }

    public boolean isPasswordTaken(String password) {
        for (int i = 0; i < mUsers.size(); i++) {
            if (mUsers.get(i).getPassword().equals(password))
                return true;
        }
        return false;
    }


}
