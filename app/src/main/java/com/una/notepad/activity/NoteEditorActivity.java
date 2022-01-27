package com.una.notepad.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.microsoft.appcenter.analytics.Analytics;
import com.una.notepad.model.Note;

import com.una.notepad.R;
import com.una.notepad.controller.NoteController;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class NoteEditorActivity extends AppCompatActivity {

    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        init();
    }


    public void init() {
        noteTitle = findViewById(R.id.noteTitle);
        noteText = findViewById(R.id.noteText);
        intent = getIntent();
        noteId = intent.getIntExtra("noteId", 0);

        if (noteId != 0) {
            noteTitle.setEnabled(false);
            noteText.setEnabled(false);

            new Thread(() -> {

                Note note = NoteController.getInstance(getApplication()).getNote(noteId);

                runOnUiThread(() -> {
                    noteTitle.setText(note.getTitle());
                    noteText.setText(note.getContent());
                    noteTitle.setEnabled(true);
                    noteText.setEnabled(true);
                });

            }).start();
        }

    }

    @Override
    public void onBackPressed() {

        new Thread(() -> {
            saveNote();
            runOnUiThread(() -> {
                super.onBackPressed();
            });
        }).start();
    }


    public void saveNote() {

        String content = noteText.getText().toString();
        String title = noteTitle.getText().toString();

        Note newNote = new Note(noteId, title, content);


        new Thread(() -> {
            NoteController.getInstance(getApplication()).save(newNote);
            Map<String, String> properties = new HashMap<>();
            properties.put("Category", "Note");

            Analytics.trackEvent("Note added", properties);


            Log.d("Success", "Nota guardada");
        }).start();


    }

    private EditText noteTitle;
    private EditText noteText;
    private Intent intent;

}
