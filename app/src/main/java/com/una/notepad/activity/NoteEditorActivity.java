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
import com.una.notepad.model.Note;

import com.una.notepad.R;
import com.una.notepad.controller.NoteController;

import java.util.Collection;
import java.util.HashSet;

public class NoteEditorActivity extends AppCompatActivity {

    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        Intent intent = getIntent();


        noteId = intent.getIntExtra("noteId",-1);

        if(noteId != -1){
            EditText editText = findViewById(R.id.noteText);
            editText.setText(controller.getNote(noteId).getContent());
        }

    }



    public void saveNote(View v){

        View editTextNote = findViewById(R.id.noteText);
        View editTextTitle = findViewById(R.id.noteTitle);


        String content = ((EditText)editTextNote).getText().toString();
        String title = ((EditText) editTextTitle).getText().toString();

        Note note = new Note(title, content);

        controller.getInstance().save(note);
        MainActivity.getNotesListView().add(title);



        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("notepad1", Context.MODE_PRIVATE);
        HashSet<String> set = new HashSet(MainActivity.getNotesListView());

        sharedPreferences.edit().putStringSet("notepad1",set).apply();

        Log.d("Success", "Nota guardada");


    }






    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.save_note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.save_note){
            controller.save(note);

            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("savedNotes", Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("nota", note.getContent());
            editor.commit();

            return true;
        }
        return false;
    }*/



    private NoteController controller = new NoteController();

}
