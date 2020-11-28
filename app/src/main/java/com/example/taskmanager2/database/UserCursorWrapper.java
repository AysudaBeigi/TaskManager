package com.example.taskmanager2.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.taskmanager2.model.User;

import java.util.Date;
import java.util.UUID;
import com.example.taskmanager2.database.TaskManagerDBSchema.UserTable.UserCols;
public class UserCursorWrapper extends CursorWrapper {

    public UserCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public User getUser(){
        UUID uuid = UUID.fromString(getString(getColumnIndex(UserCols.UUID)));
        //int id = getInt(getColumnIndex(UserCols.ID));
        String username = getString(getColumnIndex(UserCols.USERNAME));
        String password = getString(getColumnIndex(UserCols.PASSWORD));
        Date signUpDate = new Date(getLong(getColumnIndex(UserCols.SIGN_UP_DATE)));
        return new User(uuid, username, password, signUpDate);

    }

}
