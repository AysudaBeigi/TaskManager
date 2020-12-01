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
public class Task  implements Serializable {
@PrimaryKey(autoGenerate = true)
@ColumnInfo(name = TaskCols.ID)
    private long mId;
@ColumnInfo(name = TaskCols.TITLE)
    private String mTitle;
@ColumnInfo(name = TaskCols.DESCRIPTION)
    private String mDescription;
@ColumnInfo(name = TaskCols.DATE)
    private Date mDate;
@ColumnInfo(name = TaskCols.STATE)
    private TaskState mSate;
@ColumnInfo(name = TaskCols.USERNAME)
    private String mUsername;
@ColumnInfo(name = TaskCols.UUID)
    private UUID mUUID;
    /******************* CONSTRUCTOR *****************/

    public Task(String username, TaskState sate ) {
        mDate=new Date();
        mSate = sate;
        mUsername = username;
        mUUID=UUID.randomUUID();
    }

    public Task(UUID uuid, String username,
                String title, String description, Date date, TaskState sate) {
        mUUID=uuid;
        mUsername = username;
        mTitle = title;
        mDescription = description;
        mDate = date;
        mSate = sate;

    }
    /*public Task(String description, Date date, TaskState sate, String username) {
        mDescription = description;
        mDate = date;
        mUUID=UUID.randomUUID();
        mSate = sate;
        mUsername = username;
    }*/

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

    public void setDate(Date date)
    {

        mDate=date;
       /* Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(year, monthOfYear, dayOfMonth,
                mDate.getHours(), mDate.getMinutes(), mDate.getSeconds());
        mDate = calendar.getTime();
*/
    }

    public void setTime(Date time) {
        Log.d("TAG", "Task set time : "+time.toString());

        /*Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        int minute=calendar.get(Calendar.MINUTE);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        mDate=calendar.getTime();
*/
        mDate=time;
        Log.d("TAG", "Task set time : "+mDate.toString());

       /* mDate.setHours(time.getHours());
        mDate.setMinutes(time.getMinutes());
        mDate.setSeconds(time.getSeconds());
   */ }

    public void setUUID(UUID UUID) {
        mUUID = UUID;
    }

    public void setId(long id) {
        mId = id;
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

    public  String getPhotoFileName(){
        return "IMG_"+getUUID()+".jpg";
    }

}
