package com.example.taskmanager2.model;

import java.util.List;
import java.util.UUID;

public class User {

    private String mUsername;
    private String mPassword;
    private UUID mUUID;

    private List<Task> mTasks;

    public List<Task> getTasks() {
        return mTasks;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public UUID getUUID() {
        return mUUID;
    }

    public User(String username, String password) {
        mUsername = username;
        mPassword = password;
        mUUID=UUID.randomUUID();
    }
}
