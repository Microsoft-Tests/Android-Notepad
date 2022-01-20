package com.una.notepad.model;

/*
 * Note model package class using MVC pattern and SQLite Database tool
 *
 * */

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Note {

    //Will automatically increase the id primary key,
    // it means, any record (Or row) in the database table
    @PrimaryKey(autoGenerate = true)
    private int id = 0;


    public String title;
    public String content;

    public Note() {
        this.id = 0;
        this.title = "Titulo de la nota";
        this.content = "";

    }

    public Note(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString(){
       return getTitle();

    }

}
