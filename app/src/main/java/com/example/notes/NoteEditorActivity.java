package com.example.notes;

import static com.example.notes.MainActivity.notes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.HashSet;

public class NoteEditorActivity extends AppCompatActivity {
    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        EditText edit = findViewById(R.id.editText);

        Intent intent = getIntent();

        noteId = intent.getIntExtra("noteId", -1);
        if (noteId != -1) {
            edit.setText(notes.get(noteId));
        } else {
            notes.add("");
            noteId = notes.size() - 1;
            MainActivity.arrayAdapter.notifyDataSetChanged();
        }

        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                notes.set(noteId, String.valueOf(charSequence));
                MainActivity.arrayAdapter.notifyDataSetChanged();
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet(notes);
                sharedPreferences.edit().putStringSet("notes", set).apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        for (String note : notes) {
            if(note.length() == 0){
                Toast.makeText(NoteEditorActivity.this,
                        "You have created empty note!",
                        Toast.LENGTH_SHORT).show();
            }
        }
        super.onBackPressed();
    }
}

