package com.example.taskmanager2.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.taskmanager2.database.TaskManagerDBHelper;
import com.example.taskmanager2.database.UserCursorWrapper;
import com.example.taskmanager2.model.Task;
import com.example.taskmanager2.model.TaskState;
import com.example.taskmanager2.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.taskmanager2.database.TaskManagerDBSchema.UserTable.UserCols;
import  static com.example.taskmanager2.database.TaskManagerDBSchema.UserTable;

public class UserDBRepository implements IUserRepository {

  //  private List<User> mUsers;
    private List<Task> mAllTasks;
    private TaskDBRepository mTaskDBRepository;


    private static UserDBRepository sInstance;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    /********************* CONSTRUCTOR ******************/

    private UserDBRepository(Context context) {
        mContext = context.getApplicationContext();
        TaskManagerDBHelper taskManagerDBHelper = new TaskManagerDBHelper(mContext);
        mDatabase = taskManagerDBHelper.getWritableDatabase();

        Log.d("TAG", "new  uer repository and user array list ");

        // mUsers = new ArrayList<>();
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
        UserCursorWrapper userCursorWrapper = queryUserCursor(null, null);
        List<User> users = new ArrayList<>();
        if (userCursorWrapper == null || userCursorWrapper.getCount() == 0)
            return users;
        try {
            userCursorWrapper.moveToFirst();
            while (!userCursorWrapper.isAfterLast()) {
                User user = userCursorWrapper.getUser();
                users.add(user);
                userCursorWrapper.moveToNext();

            }
        } finally {
            userCursorWrapper.close();
        }
        return users;

       /*
        return mUsers;
        */
    }

    /* private User extractUserFromCursor(Cursor cursor) {
         UUID uuid = UUID.fromString(cursor.getString(cursor.getColumnIndex(UserCols.UUID)));
         int id = cursor.getInt(cursor.getColumnIndex(UserCols.ID));
         String username = cursor.getString(cursor.getColumnIndex(UserCols.USERNAME));
         String password = cursor.getString(cursor.getColumnIndex(UserCols.PASSWORD));
         Date signUpDate = new Date(cursor.getLong(cursor.getColumnIndex(UserCols.SIGN_UP_DATE)));
         return new User(id,uuid, username, password, signUpDate);
     }
 */

    /************************* QUERY  USER CURSOR *********************/

    private UserCursorWrapper queryUserCursor(String whereClause, String[] whereArgs) {
        Cursor cursor= mDatabase.query(UserTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null);
        return new UserCursorWrapper(cursor);

    }

    /******************** GET USER  WITH USERNAME *********************/
    public User getUser(String username) {
        String whereClause = UserCols.USERNAME + " = ?";
        String[] whereArgs = new String[]{
                username
        };
        UserCursorWrapper userCursorWrapper = queryUserCursor(whereClause, whereArgs);
        if (userCursorWrapper == null || userCursorWrapper.getCount() == 0)
            return null;
        try {
            userCursorWrapper.moveToFirst();
            User user = userCursorWrapper.getUser();
            return user;
        } finally {
            userCursorWrapper.close();
        }

       /*
        for (int i = 0; i < mUsers.size(); i++) {
            if (mUsers.get(i).getUsername().equals(username))
                return mUsers.get(i);
        }
        return null;*/
    }


    /********************* GET USER WITH UUID ******************/

    public User getUser(UUID uuid) {
        String whereClause = UserCols.UUID + " = ?";
        String[] whereArgs = new String[]{
                uuid.toString()};
        UserCursorWrapper userCursorWrapper = queryUserCursor(whereClause, whereArgs);
        if (userCursorWrapper == null || userCursorWrapper.getCount() == 0)
            return null;
        try {
            userCursorWrapper.moveToFirst();
            User user = userCursorWrapper.getUser();
            return user;
        } finally {
            userCursorWrapper.close();
        }

        /*for (int i = 0; i < mUsers.size(); i++) {
            if (mUsers.get(i).getUUID().equals(uuid))
                return mUsers.get(i);
        }
        return null;*/
    }

    /********************** INSERT USER ***********************/

    public void insertUser(User user) {
        Log.d("TAG", "insert user  ");
        ContentValues values = getContentValues(user);
        mDatabase.insert(UserTable.NAME,
                null,
                values);

/*
        mUsers.add(user);
*/

    }

    /************************ GET CONTENT VALUES ******************/
    public ContentValues getContentValues(User user) {
        ContentValues values = new ContentValues();
        values.put(UserCols.SIGN_UP_DATE, user.getSignUpDate().getTime());
       // values.put(UserCols.ID, user.getId());
        values.put(UserCols.UUID, user.getUUID().toString());
        values.put(UserCols.USERNAME, user.getUsername());
        values.put(UserCols.PASSWORD, user.getPassword());
        return values;
    }

    /************************ DELETE USER ************************/
    public void deleteUser(User user) {
        String whereClause = UserCols.UUID + " = ?";
        String[] whereArgs = new String[]{
                user.getUUID().toString()
        };
        mDatabase.delete(UserTable.NAME,
                whereClause,
                whereArgs);
        /*  mUsers.remove(user);
         */
    }

    /**************************** UPDATE USER *********************/
    public void updateUser(User user) {
        ContentValues values = getContentValues(user);
        String whereClause = UserCols.UUID + " = ?";
        String[] whereArgs = new String[]{
                user.getUUID().toString()};
        mDatabase.update(UserTable.NAME,
                values,
                whereClause,
                whereArgs);


       /* for (int i = 0; i < mUsers.size(); i++) {
            if (mUsers.get(i).getUUID().equals(user.getUUID())) {
                mUsers.get(i).setPassword(user.getPassword());
                mUsers.get(i).setUsername(user.getUsername());

            }
        }
*/

    }

    /************************* GET TASKS ***************************/
    public List<Task> getTasks(String username, TaskState state) {

        mTaskDBRepository = TaskDBRepository.getInstance(mContext);
        mAllTasks = mTaskDBRepository.getTasks();
        Log.d("TAG","AllTasks size is :"+mAllTasks.size());

        List<Task> userTasks = new ArrayList<>();

        for (int i = 0; i < mAllTasks.size(); i++) {
            Log.d("TAG","AllTasks.get(i).getUsername() is :"+mAllTasks.get(i).getUsername());
            Log.d("TAG","AllTasks.get(i).getstate() is :"+mAllTasks.get(i).getSate());
            if (mAllTasks.get(i).getUsername().equals(username)&&
                    mAllTasks.get(i).getSate().toString().equals(state.toString())) {
                Log.d("TAG","AllTasks.get(i) is :"+mAllTasks.get(i));
                    userTasks.add(mAllTasks.get(i));
            }
        }
        Log.d("TAG","size of userTasks is :"+userTasks.size());
        return userTasks;
    }

    /************************** IS USERNAME TAKEN ***********************/
    public boolean isUsernameTaken(String username) {
        if (getUser(username) != null)
            return true;
        return false;
    }

    /**************************** IS PASSWORD TAKEN *********************/
    public boolean isPasswordTaken(String password) {

        String whereClause = UserCols.PASSWORD + " = ?";
        String[] whereArgs = new String[]{
                password
        };
        UserCursorWrapper userCursorWrapper = queryUserCursor(whereClause, whereArgs);
        if (userCursorWrapper == null || userCursorWrapper.getCount() == 0)
            return false;
        try {
            return true;
        } finally {
            userCursorWrapper.close();
        }


       /* for (int i = 0; i < mUsers.size(); i++) {
            if (mUsers.get(i).getPassword().equals(password))
                return true;
        }
        return false;*/
    }


}
