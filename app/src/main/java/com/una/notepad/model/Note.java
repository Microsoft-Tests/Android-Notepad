package com.una.notepad.model;

public class Note {

    public Note(){
        this.id = -1;
        this.title = "Titulo de la nota";
        this.content = "";

    }

    public Note(int id, String title, String content){
        this.id = id;
        this.title = title;
        this.content = content;

    }

    public Note(String title, String content){

        this.title = title;
        this.content = content;
        this.id= -1;

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

    int id;
    String title;
    String content;
}
