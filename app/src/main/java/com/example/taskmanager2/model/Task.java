package com.example.taskmanager2.model;

import android.util.Log;
import android.widget.DatePicker;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Task  implements Serializable {

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

    public void setDate(Date date)
    {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(year, monthOfYear, dayOfMonth, mDate.getHours(), mDate.getMinutes(), mDate.getSeconds());
        mDate = calendar.getTime();

    }

    public void setTime(Date time) {
        Log.d("TAG", "Task set time : "+time.toString());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        int minute=calendar.get(Calendar.MINUTE);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        mDate=calendar.getTime();

        Log.d("TAG", "Task set time : "+mDate.toString());

       /* mDate.setHours(time.getHours());
        mDate.setMinutes(time.getMinutes());
        mDate.setSeconds(time.getSeconds());
   */ }


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
