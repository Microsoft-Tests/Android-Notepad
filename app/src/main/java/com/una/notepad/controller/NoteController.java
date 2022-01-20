package com.una.notepad.controller;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;

import androidx.room.Room;

import java.util.List;
import java.util.ArrayList;

import com.una.notepad.activity.MainActivity;
import com.una.notepad.model.Note;


public class NoteController {
    private NoteController(Application app){
        db = Room.databaseBuilder(app.getApplicationContext(),
                AppDatabase.class,
                "database-name"
        ).build();
    }

    //Adds a note using SQLite Database generated
    private boolean addNote(Note note) {
        db.NoteDao().insert(note);

        return true;
    }

    //Updates a note value
    private boolean updateNote(Note note) {
        db.NoteDao().update(note);
        return true;
    }

    //Returns a note with the specified id
    public Note getNote(int id) {
        return db.NoteDao().getNote(id);
    }

    public boolean save(Note note) {
        if (note.getId() == 0) {
            return addNote(note);
        } else {
            return updateNote(note);
        }
    }

    public boolean deleteNote(Note note){
        db.NoteDao().delete(note);
        return true;
    }

    public List<Note> getNotes() {
        return db.NoteDao().getAll();
    }

    private static NoteController instance;



    //Return Database instance
    private AppDatabase db;

    //Returns controller instance
    public static NoteController getInstance(Application app) {
        if (instance == null) {
            instance = new NoteController(app);
        }
        return instance;
    }
}
