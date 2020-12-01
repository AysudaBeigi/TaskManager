package com.example.taskmanager2.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.taskmanager2.model.Task;
import com.example.taskmanager2.model.TaskState;
import com.example.taskmanager2.model.User;

import java.util.List;
import java.util.UUID;

@Dao
public interface UserDBDAO {
    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM User")
    List<User> getUsers();

    @Query("SELECT * FROM User WHERE username=:username")
    User getUser(String username);

    @Query("SELECT * FROM User WHERE uuid=:uuid")
    User getUser(UUID uuid);

    @Query("SELECT * FROM Task WHERE username=:username AND state=:state")
    List<Task> getTasks(String username, TaskState state);

    @Query("SELECT * FROM Task WHERE username=:username")
    List<Task> getUserAllTasks(String username);

}
