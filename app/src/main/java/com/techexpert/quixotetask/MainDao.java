package com.techexpert.quixotetask;

import android.media.Image;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.techexpert.quixotetask.Model.User;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MainDao
{
    //Insert query
    @Insert(onConflict = REPLACE)
    void insert(MainData mainData);

    //Delete query
    @Delete
    void delete(MainData mainData);

    //Delete all query
    @Delete
    void reset(List<MainData> mainData);

    //Update query
    @Query("UPDATE table_name SET text1 = :sText WHERE ID = :sID")
    void update(int sID, String sText);

    //Get all data query
    @Query("SELECT *FROM table_name")
    List<MainData> getAll();

}
