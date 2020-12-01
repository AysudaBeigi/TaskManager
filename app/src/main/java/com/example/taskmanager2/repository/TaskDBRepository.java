package com.example.taskmanager2.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.room.Room;

import com.example.taskmanager2.database.TaskCursorWrapper;
import com.example.taskmanager2.database.TaskDBDAO;
import com.example.taskmanager2.database.TaskManagerDBHelper;
import com.example.taskmanager2.database.TaskManagerDBSchema;
import com.example.taskmanager2.database.TaskManagerDatabase;
import com.example.taskmanager2.database.UserCursorWrapper;
import com.example.taskmanager2.model.Task;
import com.example.taskmanager2.model.TaskState;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.FileHandler;

import com.example.taskmanager2.database.TaskManagerDBSchema.TaskTable.TaskCols;
import com.example.taskmanager2.database.TaskManagerDBSchema.TaskTable;


public class TaskDBRepository implements ITaskRepository {

  /*  private List<Task> mTasks;

    private String mUsername;
*/

    private static TaskDBRepository sInstance;
    private SQLiteDatabase mDatabase;

    private TaskDBDAO mTaskDBDAO;
    private Context mContext;

    /******************** CONSTRUCTOR ***********************/
    private TaskDBRepository(Context context) {
        mContext=context.getApplicationContext();
        TaskManagerDatabase taskManagerDatabase= Room.databaseBuilder(mContext,
                TaskManagerDatabase.class,
                TaskManagerDBSchema.NAME)
                .allowMainThreadQueries()
                .build();
        mTaskDBDAO=taskManagerDatabase.getTaskDBDAO();
       /* TaskManagerDBHelper taskManagerDBHelper = new
                TaskManagerDBHelper(context.getApplicationContext());
        mDatabase = taskManagerDBHelper.getWritableDatabase();
*/
        //  mTasks = new ArrayList<>();
    }

    /****************** GET INSTANCE *******************************/
    public static TaskDBRepository getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new TaskDBRepository(context);
            return sInstance;
        }
        return sInstance;
    }

    /************************** GET TASKS *************************/
    public List<Task> getTasks() {
         return mTaskDBDAO.getTasks();

        /*List<Task> tasks = new ArrayList<>();
        TaskCursorWrapper taskCursorWrapper = queryTaskCursor(null, null);
        if (taskCursorWrapper == null || taskCursorWrapper.getCount() == 0)
            return tasks;
        try {
            taskCursorWrapper.moveToFirst();
            while (!taskCursorWrapper.isAfterLast()) {
                Task task =taskCursorWrapper.getTask();

                tasks.add(task);
                taskCursorWrapper.moveToNext();
            }

        } finally {
            taskCursorWrapper.close();
        }
        return tasks;
*/        //return mTasks;
    }
    /*private Task extractUserFromCursor(Cursor cursor) {

        UUID uuid = UUID.fromString(cursor.getString(cursor.getColumnIndex(TaskCols.UUID)));
        int id = cursor.getInt(cursor.getColumnIndex(TaskCols.ID));
        String username = cursor.getString(cursor.getColumnIndex(TaskCols.USERNAME));
        String title = cursor.getString(cursor.getColumnIndex(TaskCols.TITLE));
        String description = cursor.getString(cursor.getColumnIndex(TaskCols.DESCRIPTION));
        Date date = new Date(cursor.getLong(cursor.getColumnIndex(TaskCols.DATE)));
        TaskState state =TaskState.valueOf(cursor.getString(cursor.getColumnIndex(TaskCols.STATE)));
        return new Task(uuid,id,username,title,description,date,state);
    }
*/



    /********************** GET TASK WITH UUID *******************/
    public Task getTask(UUID uuid) {
        return mTaskDBDAO.getTask(uuid);
        /*String whereClause = TaskCols.UUID + " = ?";
        String[] whereArgs = new String[]{
                uuid.toString()
        };
        TaskCursorWrapper taskCursorWrapper = queryTaskCursor(whereClause, whereArgs);
        if (taskCursorWrapper == null || taskCursorWrapper.getCount() == 0)
            return null;
        try {
            taskCursorWrapper.moveToFirst();
            Task task = taskCursorWrapper.getTask();
            return task;
        } finally {
            taskCursorWrapper.close();
        }*/
       /* for (int i = 0; i < mTasks.size(); i++) {
            if (mTasks.get(i).getUUID().equals(uuid))
                return mTasks.get(i);
        }
        return null;
        */
    }

    /********************* QUERY TASK CURSOR **********************/
   /* private TaskCursorWrapper queryTaskCursor(String whereClause, String[] whereArgs) {
        Cursor cursor= mDatabase.query(TaskTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null);

        return new TaskCursorWrapper(cursor);
    }
*/
    /****************** GET TASK  WITH TITLE *********************/
    public Task getTask(String title) {
       return   mTaskDBDAO.getTask(title);

       /* String whereClause = TaskCols.TITLE + " = ?";
        String[] whereArgs = new String[]{
                title
        };
        TaskCursorWrapper taskCursorWrapper = queryTaskCursor(whereClause, whereArgs);
        if (taskCursorWrapper == null || taskCursorWrapper.getCount() == 0)
            return null;
        try {
            taskCursorWrapper.moveToFirst();
            Task task = taskCursorWrapper.getTask();
            return task;
        } finally {
            taskCursorWrapper.close();
        }
*/

       /* for (int i = 0; i < mTasks.size(); i++) {
            if (mTasks.get(i).getTitle().equals(title))
                return mTasks.get(i);
        }
        return null;*/
    }

    /************************** INSET TASK **********************/
    public void insertTask(Task task) {
        mTaskDBDAO.insertTask(task);
      /*  Log.d("TAG", "insertTask");
        ContentValues values = getContentValues(task);
        mDatabase.insert(TaskTable.NAME,
                null,
                values);
*/
        //  mTasks.add(task);

    }

    /******************* GET CONTENT VALUES ***********************/

   /* public ContentValues getContentValues(Task task) {
        ContentValues values = new ContentValues();
        values.put(TaskCols.DATE, task.getDate().getTime());
        //values.put(TaskCols.ID, task.getId());
        values.put(TaskCols.UUID, task.getUUID().toString());
        values.put(TaskCols.USERNAME, task.getUsername());
        values.put(TaskCols.DESCRIPTION, task.getDescription());
        values.put(TaskCols.TITLE, task.getTitle());
        values.put(TaskCols.STATE, task.getSate().toString());
        return values;
    }
*/

    /******************** DELETE TASK WITH TASK**************************/
    public void deleteTask(Task task) {
        mTaskDBDAO.deleteTask(task);
       /* String whereClause = TaskCols.UUID + " = ?";
        String[] whereArgs = new String[]{
                task.getUUID().toString()
        };
        mDatabase.delete(TaskTable.NAME,
                whereClause,
                whereArgs);
*/
        // mTasks.remove(task);

    }

    /*******************DELETE TASK WITH UUID *******************/
   // public void deleteTask(UUID taskUuid) {
        /*String whereClause = TaskCols.UUID + " = ?";
        String[] whereArgs = new String[]{
                taskUuid.toString()
        };
        mDatabase.delete(TaskTable.NAME,
                whereClause,
                whereArgs);
*/
        /*for (int i = 0; i <mTasks.size() ; i++) {
            if(mTasks.get(i).getUUID().equals(taskUuid))
                mTasks.remove(mTasks.get(i));
        }*/

    //}

    /*********************** UPDATE TASK **********************/
    public void updateTask(Task task) {
        mTaskDBDAO.updateTask(task);
       /* ContentValues values = getContentValues(task);
        String whereClause = TaskCols.UUID + " = ?";
        String[] whereArgs = new String[]{
                task.getUUID().toString()
        };
        mDatabase.update(TaskTable.NAME,
                values,
                whereClause,
                whereArgs);
*/

       /* Task mTask=getTask(task.getUUID());
        if(mTask!=null){
            mTask.setTitle(task.getTitle());
            mTask.setDescription(task.getDescription());
            mTask.setDate(task.getDate());
            mTask.setSate(task.getSate());
        }*/

    }

    public File getPhotoFile(Task task){
        File filesDir=mContext.getFilesDir();
        if(task!=null){
        File photoFile=new File(filesDir,task.getPhotoFileName());
        return photoFile;

        }
        return null;
    }


}
