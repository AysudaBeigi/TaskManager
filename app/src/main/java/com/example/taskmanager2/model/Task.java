package com.example.taskmanager2.model;

import android.util.Log;

import com.example.taskmanager2.database.TaskManagerDBSchema.TaskTable.TaskCols;
import com.example.taskmanager2.database.TaskManagerDBSchema.TaskTable;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.taskmanager2.database.TaskManagerDBSchema;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Entity
public class Task implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = TaskCols.ID)
    private long mId;
    @ColumnInfo(name = TaskCols.TITLE)
    private String mTitle;
    @ColumnInfo(name = TaskCols.DESCRIPTION)
    private String mDescription;
    @ColumnInfo(name = TaskCols.DATE)
    private Date mDate;
    @ColumnInfo(name = TaskCols.TIME)
    private Date mTime;
    @ColumnInfo(name = TaskCols.STATE)
    private TaskState mSate;
    @ColumnInfo(name = TaskCols.USERNAME)
    private String mUsername;
    @ColumnInfo(name = TaskCols.UUID)
    private UUID mUUID;
    @ColumnInfo(name = TaskCols.PHOTO_ADDRESS)
    private String mPhotoAddress;

    /******************* CONSTRUCTOR *****************/

    public Task(String username, TaskState sate) {
        mDate = new Date();
        mTime = new Date();
        mSate = sate;
        mUsername = username;
        mUUID = UUID.randomUUID();
        mPhotoAddress = "";
    }

    public Task(UUID uuid, String username,
                String title, String description, Date date, Date time,
                TaskState sate, String photoAddress) {
        mUUID = uuid;
        mUsername = username;
        mTitle = title;
        mDescription = description;
        mDate = date;
        mTime = time;
        mSate = sate;
        mPhotoAddress = photoAddress;

    }

    /******************* SETTER ************************/
    public void setSate(TaskState sate) {
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

        mDate=date;
    }

    public void setTime(Date time) {
        Log.d("TAG", "Task set time : " + time.toString());

        mTime=time;
    }

    public void setUUID(UUID UUID) {
        mUUID = UUID;
    }

    public void setId(long id) {
        mId = id;
    }

    public void setPhotoAddress(String photoAddress) {
        mPhotoAddress = photoAddress;
    }

    /********************** GETTER ***********************/
    public TaskState getSate() {

        return mSate;
    }

    public UUID getUUID() {
        return mUUID;
    }

    public long getId() {
        return mId;
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


    public String getPhotoFileName() {
        return "IMG_" + getUUID() + ".jpg";
    }

    public String getPhotoAddress() {
        return mPhotoAddress;
    }
}
