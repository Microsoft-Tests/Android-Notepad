package com.una.notepad.model;

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

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM note")
    List<Note> getAll();

    @Query("Select * from note where id=:id")
    Note getNote(int id);

    @Insert
    void insert(Note... notes);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);
}
