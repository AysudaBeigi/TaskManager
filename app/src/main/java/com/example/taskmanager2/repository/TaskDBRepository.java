package com.example.taskmanager2.repository;

import com.example.taskmanager2.model.Task;
import com.example.taskmanager2.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskDBRepository {

    private List<Task> mTasks;

    private String mUsername;

    private static TaskDBRepository sInstance;


    private TaskDBRepository() {

        mTasks = new ArrayList<>();
    }

    public static TaskDBRepository getInstance() {
        if (sInstance == null) {
            sInstance = new TaskDBRepository();
            return sInstance;
        }
        return sInstance;
    }

    public List<Task> getTasks() {
        return mTasks;
    }

    public Task getTask(UUID uuid) {
        for (int i = 0; i < mTasks.size(); i++) {
            if (mTasks.get(i).getUUID().equals(uuid))
                return mTasks.get(i);
        }
        return null;
    }


    public Task getTask(String title) {
        for (int i = 0; i < mTasks.size(); i++) {
            if (mTasks.get(i).getTitle().equals(title))
                return mTasks.get(i);
        }
        return null;
    }




    public void insertTask(Task task) {
        mTasks.add(task);

    }

    public void deleteTask(Task task) {
        mTasks.remove(task);

    }


    public void updateTask(Task task) {
        Task mTask=getTask(task.getUUID());
        if(mTask!=null){
            mTask.setTitle(task.getTitle());
            mTask.setDescription(task.getDescription());
            mTask.setDate(task.getDate());
            mTask.setSate(task.getSate());
            mTask.setTime(task.getTime());
        }

    }



}
