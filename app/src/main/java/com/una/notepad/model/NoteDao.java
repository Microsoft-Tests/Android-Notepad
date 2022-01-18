package com.una.notepad.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * from note")
    List<Note> getAll();

    @Query("Select * from note where id=:id")
    Note getNote(int id);

    @Insert
    void insertAll(Note... notes);

    @Delete
    void delete(Note note);

    @Update
    void update(Note note);
}
