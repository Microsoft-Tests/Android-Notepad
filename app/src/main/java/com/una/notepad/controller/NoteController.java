package com.una.notepad.controller;

import java.util.List;
import java.util.ArrayList;
import com.una.notepad.model.Note;


public class NoteController {
    //Returns controller instance
    public static NoteController getInstance(){
        if(instance == null){
            instance = new NoteController();
        }
        return instance;
    }
    //Adds a note
    private boolean addNote(String title, String content){
        int id = notes.size();
        Note newNote = new Note(id, title, content);
        return notes.add(newNote);
    }
    //Updates a note value
    private boolean updateNote(Note note){
        Note noteToUpdate = getNote(note.getId());

        if(noteToUpdate != null){
            noteToUpdate.setTitle(note.getTitle());
            noteToUpdate.setContent(note.getContent());
            return true;
        }
        return false;
    }
    //Returns a note with the specified id
    public Note getNote(int id){
        return notes.get(id);
    }

    public boolean save(Note note){
        if(note.getId() == -1){
            addNote(note.getTitle(), note.getContent());



            return true;
        }else{
            return updateNote(note);
        }
    }

    public List<Note> getNotes(){
        return notes;

    }

    private List<Note> notes = new ArrayList<Note>();
    private static NoteController instance;
}
