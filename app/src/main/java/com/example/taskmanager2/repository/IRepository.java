package com.example.taskmanager2.repository;

import com.example.taskmanager2.model.User;

import java.util.List;
import java.util.UUID;

public interface IRepository {
    public List<User> getUsers();
    public User getUser(String username);
    public User getUser(UUID uuid);
    public void insertUser(User user);
    public void deleteUser(User user);
    public void updateUser(User user);


}
