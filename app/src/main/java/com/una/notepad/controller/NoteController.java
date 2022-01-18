package com.una.notepad.controller;

import android.app.Application;
import android.util.Log;

import androidx.room.Room;

import java.util.List;
import com.una.notepad.model.Note;
import com.una.notepad.model.NoteDao;


public class NoteController {

    private NoteController(){}

    //Adds a note
    private boolean addNote(Note note){
        db.noteDao().insertAll(note);
        return true;
    }
    //Updates a note value
    private boolean updateNote(Note note){
        db.noteDao().update(note);
        return true;
    }
    //Returns a note with the specified id
    public Note getNote(int id){
        return db.noteDao().getNote(id);
    }

    public boolean save(Note note){
        if(note.id == 0){
            return addNote(note);
        }else{
            return updateNote(note);
        }
    }
    public boolean deleteNote(Note note) {
        db.noteDao().delete(note);
        return true;
    }

    public List<Note> getNotes(){
        return db.noteDao().getAll();
    }

    private AppDatabase db;
    private static NoteController instance;
    public static NoteController getInstance(Application app){
        if(instance == null){
            instance = new NoteController();
            instance.db = Room.databaseBuilder(
                    app.getApplicationContext(),
                    AppDatabase.class,
                    "notes-db"
            ).build();
        }
        return instance;
    }


}
