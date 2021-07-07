package com.example.taskmanager2;

import android.content.Context;

import androidx.room.Room;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.test.core.app.ApplicationProvider;

import com.example.taskmanager2.database.TaskDBDAO;
import com.example.taskmanager2.database.TaskManagerDatabase;
import com.example.taskmanager2.database.UserDBDAO;
import com.example.taskmanager2.model.Task;
import com.example.taskmanager2.model.TaskState;
import com.example.taskmanager2.model.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class DAOUnitTest {

    private UserDBDAO userDao;
    private TaskDBDAO taskDao;
    private TaskManagerDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TaskManagerDatabase.class).build();
        userDao = db.getUserDBDAO();
        taskDao = db.getTaskDBDAO();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeUserAndReadInList() throws Exception {
        User user = new User("username", "password");
        user.setUsername("george");
        userDao.insertUser(user);
        List<User> users = userDao.getUsers();
        assertThat(users.get(0), equalTo(user));

        user.setUsername("ali");
        userDao.updateUser(user);
        assertThat(users.get(0), equalTo(user));

        User specialUser = userDao.getUser("username");
        List<Task> userTasks=userDao.getTasks("username", TaskState.DOING);

        Task task=new Task("username",TaskState.DOING);
        taskDao.insertTask(task);
        List<Task> tasks=taskDao.getTasks();
        Task specialTask=taskDao.getTask("title");
        assertThat(tasks.get(0),equalTo(task));

        task.setTitle("new title");
        taskDao.updateTask(task);
        assertThat(tasks.get(0),equalTo(task));


    }
}

