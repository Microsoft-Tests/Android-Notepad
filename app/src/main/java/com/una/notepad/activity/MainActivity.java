package com.una.notepad.activity;


import android.content.Intent;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.una.notepad.R;
import com.una.notepad.controller.NoteController;
import com.una.notepad.model.Note;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    void init() {
        listView = findViewById(R.id.listView);
        notes = new ArrayList<>();
        listView.setOnItemClickListener((adapterView, view, pos, id) -> {
            Intent intent = new Intent(getApplicationContext(), NoteEditorActivity.class);
            Note note = notes.get(pos);
            intent.putExtra("noteId", note.id);
            startActivity(intent);
        });
        listView.setOnItemLongClickListener((adapterView, view, pos, id) -> {
            Note note = notes.get(pos);
            new Thread(() -> {
                NoteController.getInstance(getApplication()).deleteNote(note);
                update();
            }).start();
            return false;
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        update();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.add_note) {
            Intent intent = new Intent(this, NoteEditorActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }


    public void update() {
        Thread th = new Thread(() -> {
            notes = NoteController.getInstance(getApplication()).getNotes();
            runOnUiThread(() -> {
                notesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, notes);
                listView.setAdapter(notesAdapter);
                notesAdapter.notifyDataSetChanged();
            });
        });
        th.start();
    }

    private List<Note> notes;
    private ArrayAdapter<Note> notesAdapter;
}
