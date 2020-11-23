package com.example.taskmanager2.model;

import android.widget.DatePicker;

import java.util.Date;
import java.util.UUID;

public class Task {

    private String mTitle;
    private String mDescription;
    private Date mDate;
    private TaskSate mSate;
    private String mUsername;
    private UUID mUUID;
    /******************* CONSTRUCTOR *****************/

    public Task(String username,TaskSate sate ) {
        mDate=new Date();
        mSate = sate;
        mUsername = username;
        mUUID=UUID.randomUUID();
    }

    public Task(String title, String description, Date date, TaskSate sate, String username) {
        mTitle = title;
        mDescription = description;
        mDate = date;
        mUUID=UUID.randomUUID();
        mSate = sate;
        mUsername = username;

    }

    public Task(String description, Date date,  TaskSate sate, String username) {
        mDescription = description;
        mDate = date;
        mUUID=UUID.randomUUID();
        mSate = sate;
        mUsername = username;
    }





    /******************* SETTER ************************/
    public void setSate(TaskSate sate) {
        mSate = sate;
    }



    public void setUsername(String username) {
        mUsername = username;
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

    /********************** GETTER ***********************/
    public TaskSate getSate() {

        return mSate;
    }

    public UUID getUUID() {
        return mUUID;
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


}
