package com.una.notepad.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import com.una.notepad.model.Note;

import com.una.notepad.R;
import com.una.notepad.controller.NoteController;


public class NoteEditorActivity extends AppCompatActivity {

    int noteId;
    EditText title = null;
    EditText content = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        init();
        load();
    }

    private void init(){
        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId", 0);

        title = findViewById(R.id.noteTitle);
        content = findViewById(R.id.noteText);
    }

    private void load(){
        title.setEnabled(false);
        content.setEnabled(false);
        new Thread(()->{
            Note note = NoteController.getInstance(getApplication()).getNote(noteId);
            runOnUiThread(()->{
                if(note != null){
                    title.setText(note.title);
                    content.setText(note.content);
                }
                title.setEnabled(true);
                content.setEnabled(true);
            });
        }).start();

    }

    private void saveNote(){
        String strTitle = title.getText().toString();
        String strContent = content.getText().toString();
        if(!strTitle.isEmpty()){
            NoteController.getInstance(getApplication()).save(new Note(noteId, strTitle, strContent));
            Log.v("Success", "Nota guardada");
        }
    }

    @Override
    public void onBackPressed() {

        new Thread(()->{
            saveNote();
            runOnUiThread(()->{
                super.onBackPressed();
            });
        }).start();
    }

}
