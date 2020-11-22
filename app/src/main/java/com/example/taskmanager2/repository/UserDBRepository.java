package com.example.taskmanager2.repository;

import com.example.taskmanager2.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDBRepository {

    private List<User> mUsers;

    private static UserDBRepository sInstance;


    private UserDBRepository() {

        mUsers = new ArrayList<>();
    }

    public static UserDBRepository getInstance() {
        if (sInstance == null)
            return new UserDBRepository();
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


}
