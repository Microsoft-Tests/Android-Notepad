package com.una.notepad.controller;

/* My mentor concluded that the best way to storage data in this
 * application is using a local Database using Room storage
 *
 * We used the next web page as a guide
 *
 * https://developer.android.com/training/data-storage/room
 *
 * It uses SQLite Database tool
 *
 *  */

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.una.notepad.model.Note;
import com.una.notepad.model.NoteDao;

@Database(entities = {Note.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract NoteDao NoteDao();
}

