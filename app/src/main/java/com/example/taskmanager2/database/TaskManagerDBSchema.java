package com.example.taskmanager2.database;

import androidx.annotation.NonNull;

public class TaskManagerDBSchema {
    public static final String NAME = "taskManager.db";
    public static final int VERSION = 1;

    public static final class UserTable {
        public static final String NAME = "user_table";

        public static final class UserCols {
            public static final String USERNAME = "username";
            public static final String PASSWORD = "password";
            public static final String UUID = "uuid";
            public static final String ID = "id";
            public static final String SIGN_UP_DATE = "sign_up_date";
        }

    }

    public static final class TaskTable {

        public static final String NAME = "task_table";

        public static final class TaskCols {
            public static final String ID = "id";
            public static final String TITLE = "title";
            public static final String DESCRIPTION = "description";
            public static final String STATE = "state";
            public static final String DATE = "date";
            public static final String UUID = "uuid";
            public static final String USERNAME = "username";
        }

    }


}
