package com.example.taskmanager2.repository;

import com.example.taskmanager2.model.Task;

import java.util.List;
import java.util.UUID;

public interface ITaskRepository {

    public List<Task> getTasks();
    public Task getTask(UUID uuid);
    public Task getTask(String title);
    public void insertTask(Task task);
    public void deleteTask(Task task);
    public void deleteTask(UUID taskUuid);
    public void updateTask(Task task);



}
