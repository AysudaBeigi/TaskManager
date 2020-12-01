package com.example.taskmanager2.database;

import androidx.room.TypeConverter;

import com.example.taskmanager2.model.Task;
import com.example.taskmanager2.model.TaskState;

import java.util.Date;
import java.util.UUID;

public class Converter {


    @TypeConverter
    public static TaskState StringToState(String stateStr) {
        switch (stateStr) {
            case "TODO":
                return TaskState.TODO;
            case "DOING":
                return TaskState.DOING;
            case "DONE":
                return TaskState.DONE;
            default:
                return null;
        }

    }
    @TypeConverter
    public static String StateToString(TaskState taskState) {
        return taskState.toString();
    }

    @TypeConverter
    public static UUID stringToUUID(String value) {
        return UUID.fromString(value);
    }

    @TypeConverter
    public static String UUIDToString(UUID uuid) {
        return uuid.toString();
    }

    @TypeConverter
    public static Date LongToDate(Long timeStamp) {
        return timeStamp == null ? null : new Date(timeStamp);
    }

    @TypeConverter
    public static Long DateToLong(Date date) {
        return date == null ? null : date.getTime();
    }
}
