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

import com.microsoft.appcenter.distribute.Distribute;
import com.una.notepad.BuildConfig;
import com.una.notepad.R;
import com.una.notepad.controller.NoteController;
import com.una.notepad.model.MyDistributeListener;
import com.una.notepad.model.Note;
import java.util.List;

import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Distribute.setListener(new MyDistributeListener());
        AppCenter.start(getApplication(), "c8c96064-0c18-490f-8d03-8a71669c2886", Distribute.class,
                Analytics.class, Crashes.class);

        init();

        Distribute.checkForUpdate();
    }

    public void init() {

        listView = findViewById(R.id.listView);
        update();

        listView.setOnItemClickListener((adapterView, view, pos, l) -> {

                    Intent intent = new Intent(this, NoteEditorActivity.class);
                    Note note = notes.get(pos);
                    intent.putExtra("noteId", note.getId());
                    startActivity(intent);
                }
        );

        listView.setOnItemLongClickListener((adapterView, view, pos, l) -> {
                    new Thread(() -> {
                        Note note = notes.get(pos);
                        NoteController.getInstance(getApplication()).deleteNote(note);
                        notes = NoteController.getInstance(getApplication()).getNotes();

                        runOnUiThread(() -> {
                            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, notes);
                            listView.setAdapter(arrayAdapter);

                        });

                    }).start();

                    return true;
                }
        );
    }

    @Override
    public void onResume() {
        update();
        super.onResume();
    }

    public void update() {

        new Thread(() -> {
            notes = NoteController.getInstance(getApplication()).getNotes();

            runOnUiThread(() -> {
                arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, notes);
                listView.setAdapter(arrayAdapter);
            });
        }).start();
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

    static List<Note> notes;
    static ArrayAdapter arrayAdapter;
    private ListView listView;
}
