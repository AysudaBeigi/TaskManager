package com.example.taskmanager2.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.taskmanager2.model.Task;

import java.util.List;
import java.util.UUID;

@Dao
public interface TaskDBDAO {

    @Query("SELECT * FROM Task ")
    List<Task> getTasks();

    @Query("SELECT * FROM Task WHERE uuid=:uuid")
    Task getTask(UUID uuid);

    @Query("SELECT * FROM Task WHERE title=:title")
    Task getTask(String title);

    @Insert
    void insertTask(Task task);

    @Delete
    void deleteTask(Task task);

    @Update
    void updateTask(Task task);


}
