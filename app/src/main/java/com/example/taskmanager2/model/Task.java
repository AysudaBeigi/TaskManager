package com.example.taskmanager2.model;

import android.widget.DatePicker;

import java.util.Date;

public class Task {

    private String mTitle;
    private String mDescription;
    private Date mDate;
    private Date mTime;
    private TaskSate mSate;
    private User mUser;
    private String mUsername;

    public void setSate(TaskSate sate) {
        mSate = sate;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public TaskSate getSate() {
        return mSate;
    }

    public User getUser() {
        return mUser;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public Date getDate() {
        return mDate;
    }

    public Date getTime() {
        return mTime;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public void setTime(Date time) {
        mTime = time;
    }

}
