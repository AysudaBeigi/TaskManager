package com.example.taskmanager2.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class User {

    private String mUsername;
    private String mPassword;
    private UUID mUUID;
    private final Date mSignUpDate;

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

    public Date getSignUpDate() {
        return mSignUpDate;
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
        mSignUpDate=new Date();
    }
}
