package com.una.notepad.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Note {
    public Note(){}
    public Note(String title, String content){
        this(0, title, content);
    }
    public Note(int id, String title, String content){
        this.id = id;
        this.title = title;
        this.content = content;
    }

    @PrimaryKey(autoGenerate = true)
    public int id = 0;

    public String title;
    public String content;


    @Override
    public String toString(){
        return title;
    }
}
