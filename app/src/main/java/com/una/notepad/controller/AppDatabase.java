package com.una.notepad.controller;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.una.notepad.model.Note;
import com.una.notepad.model.NoteDao;

@Database(entities = {Note.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();
}
