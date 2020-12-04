package com.example.taskmanager2.repository;

import com.example.taskmanager2.model.Task;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ITaskRepository {

    List<Task> getTasks();

    Task getTask(UUID uuid);

    Task getTask(String title);

    void insertTask(Task task);

    void deleteTask(Task task);

    void updateTask(Task task);

    File getPhotoFile(Task task) throws IOException;


}
