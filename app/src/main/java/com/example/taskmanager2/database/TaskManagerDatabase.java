package com.example.taskmanager2.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.taskmanager2.model.Task;
import com.example.taskmanager2.model.User;

@Database(entities = {Task.class, User.class}, version = TaskManagerDBSchema.VERSION)
@TypeConverters({Converter.class})
public abstract class TaskManagerDatabase extends RoomDatabase {
    public abstract UserDBDAO getUserDBDAO();

    public abstract TaskDBDAO getTaskDBDAO();

}
