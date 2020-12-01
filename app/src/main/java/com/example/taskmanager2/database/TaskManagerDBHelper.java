package com.example.taskmanager2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;
import com.example.taskmanager2.database.TaskManagerDBSchema.UserTable.UserCols;
import com.example.taskmanager2.database.TaskManagerDBSchema.TaskTable.TaskCols;

public class TaskManagerDBHelper extends SQLiteOpenHelper {

    public TaskManagerDBHelper(@Nullable Context context) {

        super(context, TaskManagerDBSchema.NAME, null, TaskManagerDBSchema.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        generateUserQuery(db);

        generateTaskQuery(db);

    }

    private void generateTaskQuery(SQLiteDatabase db) {
        Log.d("TAG","generateTaskQuery");
        db.execSQL( "CREATE TABLE " + TaskManagerDBSchema.TaskTable.NAME + "(" +
                TaskCols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TaskCols.UUID + "  TEXT NOT NULL ," +
                TaskCols.USERNAME + "  TEXT NOT NULL ," +
                TaskCols.TITLE + " TEXT  ," +
                TaskCols.DESCRIPTION + " TEXT ," +
                TaskCols.DATE + " TEXT NOT NULL," +
                TaskCols.STATE + " TEXT NOT NULL );");
    }

    private void generateUserQuery(SQLiteDatabase db) {
        Log.d("TAG","generateUserQuery");

        db.execSQL("CREATE TABLE " + TaskManagerDBSchema.UserTable.NAME + "(" +
                UserCols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                UserCols.UUID + "  TEXT NOT NULL ," +
                UserCols.USERNAME + "  TEXT NOT NULL ," +
                UserCols.PASSWORD + " TEXT NOT NULL ," +
                UserCols.SIGN_UP_DATE + " TEXT NOT NULL );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
