package com.example.taskmanager2.model;

import com.example.taskmanager2.database.TaskManagerDBSchema.UserTable.UserCols;
import com.example.taskmanager2.database.TaskManagerDBSchema.UserTable;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class User implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = UserCols.ID)
    private long mId;
    @ColumnInfo(name = UserCols.USERNAME)
    private String mUsername;
    @ColumnInfo(name = UserCols.PASSWORD)
    private String mPassword;
    @ColumnInfo(name = UserCols.UUID)
    private UUID mUUID;
    @ColumnInfo(name = UserCols.SIGN_UP_DATE)
    private  Date mSignUpDate;

    public long getId() {
        return mId;
    }

    public void setUUID(UUID UUID) {
        mUUID = UUID;
    }

    public void setId(long id) {
        mId = id;
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

    public void setSignUpDate(Date signUpDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(signUpDate);

        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(year, monthOfYear, dayOfMonth,
                mSignUpDate.getHours(), mSignUpDate.getMinutes(), mSignUpDate.getSeconds());
        mSignUpDate = calendar.getTime();

    }

    public UUID getUUID() {
        return mUUID;
    }

    public User(String username, String password) {
        mUsername = username;
        mPassword = password;
        mUUID = UUID.randomUUID();
        mSignUpDate = new Date();
    }

    public User(UUID uuid, String username, String password, Date signUpDate) {
        mUUID = uuid;
        mUsername = username;
        mPassword = password;
        mSignUpDate = signUpDate;
    }


}
