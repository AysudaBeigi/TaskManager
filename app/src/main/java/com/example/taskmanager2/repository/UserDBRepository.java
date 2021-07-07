package com.example.taskmanager2.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.room.Room;

import com.example.taskmanager2.database.TaskManagerDBHelper;
import com.example.taskmanager2.database.TaskManagerDBSchema;
import com.example.taskmanager2.database.TaskManagerDatabase;
import com.example.taskmanager2.database.UserCursorWrapper;
import com.example.taskmanager2.database.UserDBDAO;
import com.example.taskmanager2.model.Task;
import com.example.taskmanager2.model.TaskState;
import com.example.taskmanager2.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.taskmanager2.database.TaskManagerDBSchema.UserTable.UserCols;
import  static com.example.taskmanager2.database.TaskManagerDBSchema.UserTable;

public class UserDBRepository implements IUserRepository {



    private static UserDBRepository sInstance;
    private Context mContext;
    private UserDBDAO mUserDBDAO;

    /********************* CONSTRUCTOR ******************/

    private UserDBRepository(Context context) {
        mContext = context.getApplicationContext();
        TaskManagerDatabase taskManagerDatabase= Room.databaseBuilder(
                mContext,
                TaskManagerDatabase.class,
                TaskManagerDBSchema.NAME)
                .allowMainThreadQueries()
                .build();
        mUserDBDAO=taskManagerDatabase.getUserDBDAO();


    }

    /******************* GET INSTANCE ********************/
    public static UserDBRepository getInstance(Context context) {

        if (sInstance == null) {
            Log.d("TAG", "sInstance is null ");
            sInstance = new UserDBRepository(context);
            return sInstance;
        }
        Log.d("TAG", "sInstance is not null ");

        return sInstance;
    }

    /******************** GET USERS **********************/
    public List<User> getUsers() {
        return mUserDBDAO.getUsers();

    }

    /******************** GET USER  WITH USERNAME *********************/
    public User getUser(String username) {
       return mUserDBDAO.getUser(username);

    }


    /********************* GET USER WITH UUID ******************/

    public User getUser(UUID uuid) {
        return mUserDBDAO.getUser(uuid);
    }

    /********************** INSERT USER ***********************/

    public void insertUser(User user) {
        mUserDBDAO.insertUser(user);
    }

    /************************ DELETE USER ************************/
    public void deleteUser(User user) {
        mUserDBDAO.deleteUser(user);

    }

    /**************************** UPDATE USER *********************/
    public void updateUser(User user) {
        mUserDBDAO.updateUser(user);
    }

    /************************* GET TASKS ***************************/
    public List<Task> getTasks(String username, TaskState state) {
        return mUserDBDAO.getTasks(username,state);

    }


    public List<Task> getUserAllTasks(String username){
        return mUserDBDAO.getUserAllTasks(username);

    }


}
