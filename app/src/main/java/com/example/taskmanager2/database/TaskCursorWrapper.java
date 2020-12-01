package com.example.taskmanager2.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.taskmanager2.model.Task;
import com.example.taskmanager2.model.TaskState;
import com.example.taskmanager2.model.User;

import java.util.Date;
import java.util.UUID;
import com.example.taskmanager2.database.TaskManagerDBSchema.TaskTable.TaskCols;
public class TaskCursorWrapper extends CursorWrapper {

    public TaskCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    public Task getTask(){

        UUID uuid = UUID.fromString(getString(getColumnIndex(TaskCols.UUID)));
        String username = getString(getColumnIndex(TaskCols.USERNAME));
        String title = getString(getColumnIndex(TaskCols.TITLE));
        String description = getString(getColumnIndex(TaskCols.DESCRIPTION));
        String photoAddress = getString(getColumnIndex(TaskCols.PHOTO_ADDRESS));
        Date date = new Date(getLong(getColumnIndex(TaskCols.DATE)));
        TaskState state =TaskState.valueOf(getString(getColumnIndex(TaskCols.STATE)));
        return new Task(uuid,username,title,description,date,state,photoAddress);

    }

}
