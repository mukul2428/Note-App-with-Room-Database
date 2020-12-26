package com.techexpert.quixotetask;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.techexpert.quixotetask.Model.User;


@Dao
public interface UserDao {
    @Query("SELECT * FROM User where email= :mail and password= :password")
    User getUser(String mail, String password);

    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);
}
