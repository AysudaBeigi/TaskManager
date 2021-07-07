package com.example.taskmanager2.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.room.Room;

import com.example.taskmanager2.database.TaskCursorWrapper;
import com.example.taskmanager2.database.TaskDBDAO;
import com.example.taskmanager2.database.TaskManagerDBHelper;
import com.example.taskmanager2.database.TaskManagerDBSchema;
import com.example.taskmanager2.database.TaskManagerDatabase;
import com.example.taskmanager2.database.UserCursorWrapper;
import com.example.taskmanager2.model.Task;
import com.example.taskmanager2.model.TaskState;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.FileHandler;

import com.example.taskmanager2.database.TaskManagerDBSchema.TaskTable.TaskCols;
import com.example.taskmanager2.database.TaskManagerDBSchema.TaskTable;


public class TaskDBRepository implements ITaskRepository {
    private static TaskDBRepository sInstance;
    private TaskDBDAO mTaskDBDAO;
    private Context mContext;

    /******************** CONSTRUCTOR ***********************/
    private TaskDBRepository(Context context) {
        mContext = context.getApplicationContext();
        TaskManagerDatabase taskManagerDatabase = Room.databaseBuilder(mContext,
                TaskManagerDatabase.class,
                TaskManagerDBSchema.NAME)
                .allowMainThreadQueries()
                .build();
        mTaskDBDAO = taskManagerDatabase.getTaskDBDAO();

    }

    /****************** GET INSTANCE *******************************/
    public static TaskDBRepository getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new TaskDBRepository(context);
            return sInstance;
        }
        return sInstance;
    }

    /************************** GET TASKS *************************/
    public List<Task> getTasks() {
        return mTaskDBDAO.getTasks();


    }


    /********************** GET TASK WITH UUID *******************/
    public Task getTask(UUID uuid) {
        return mTaskDBDAO.getTask(uuid);

    }

    /****************** GET TASK  WITH TITLE *********************/
    public Task getTask(String title) {
        return mTaskDBDAO.getTask(title);

    }

    /************************** INSET TASK **********************/
    public void insertTask(Task task) {
        mTaskDBDAO.insertTask(task);


    }


    /******************** DELETE TASK WITH TASK**************************/
    public void deleteTask(Task task) {
        mTaskDBDAO.deleteTask(task);

    }



    /*********************** UPDATE TASK **********************/
    public void updateTask(Task task) {
        mTaskDBDAO.updateTask(task);


    }

    /***************************  GET PHOTO FILE ****************************/
    public File getPhotoFile(Task task) throws IOException {
        File filesDir = mContext.getFilesDir();
        if (task != null) {
            File photoFile = new File(filesDir, task.getPhotoFileName());
            return photoFile;

        }
        throw new IOException();


    }

}
