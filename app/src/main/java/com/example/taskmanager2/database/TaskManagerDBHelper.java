package com.example.taskmanager2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import com.example.taskmanager2.database.TaskManagerDBSchema.UserTable.UserCols;
import com.example.taskmanager2.database.TaskManagerDBSchema.TaskTable.TaskCols;

public class TaskManagerDBHelper extends SQLiteOpenHelper {

    public TaskManagerDBHelper(
            @Nullable Context context, @Nullable String name,
            @Nullable SQLiteDatabase.CursorFactory factory, int version) {

        super(context, TaskManagerDBSchema.NAME, null, TaskManagerDBSchema.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        generateUserQuery(db);

        generateTaskquery(db);


    }

    private void generateTaskquery(SQLiteDatabase db) {
        StringBuilder taskSbQuery=new StringBuilder();
        taskSbQuery.append("CREATE_TABLE"+ TaskManagerDBSchema.UserTable.NAME+"(");
        taskSbQuery.append(TaskCols.ID+"INTEGER PRIMARY KEY AUTOINCREMENT,");
        taskSbQuery.append(TaskCols.UUID+"  TEXT NOT NULL ,");
        taskSbQuery.append(TaskCols.USERNAME+"  TEXT NOT NULL ," );
        taskSbQuery.append(TaskCols.TITLE+" TEXT  ,");
        taskSbQuery.append(TaskCols.DESCRIPTION+"TEXT ,");
        taskSbQuery.append(TaskCols.DATE+"TEXT NOT NULL,");
        taskSbQuery.append(TaskCols.STATE+"TEXT NOT NULL,");
        taskSbQuery.append(");");
        db.execSQL(taskSbQuery.toString());
    }

    private void generateUserQuery(SQLiteDatabase db) {
        StringBuilder userSbQuery = new StringBuilder();
        userSbQuery.append("CREATE_TABLE" + TaskManagerDBSchema.UserTable.NAME + "(");
        userSbQuery.append(UserCols.ID + "INTEGER PRIMARY KEY AUTOINCREMENT,");
        userSbQuery.append(UserCols.UUID + "  TEXT NOT NULL ,");
        userSbQuery.append(UserCols.USERNAME + "  TEXT NOT NULL ,");
        userSbQuery.append(UserCols.PASSWORD + " TEXT NOT NULL ,");
        userSbQuery.append(UserCols.SIGN_UP_DATE + " TEXT NOT NULL  ,");
        userSbQuery.append(");");
        db.execSQL(userSbQuery.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
