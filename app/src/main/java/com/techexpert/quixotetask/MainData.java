package com.techexpert.quixotetask;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

//define table name
@Entity(tableName = "table_name")
public class MainData implements Serializable
{

    //create id column
    @PrimaryKey(autoGenerate = true)
    private  int ID;

    //create text column
    @ColumnInfo(name = "text1")
    private String text1;
    //create text column
    @ColumnInfo(name = "text2")
    private String text2;
    //create text column
    @ColumnInfo(name = "img")
    private String img;

    //generate getters and setters


    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getID() {
        return ID;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

}
