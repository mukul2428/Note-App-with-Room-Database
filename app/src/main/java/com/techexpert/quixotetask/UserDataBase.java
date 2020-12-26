package com.techexpert.quixotetask;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.techexpert.quixotetask.Model.User;


@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDataBase extends RoomDatabase {

    public abstract UserDao getUserDao();

}

