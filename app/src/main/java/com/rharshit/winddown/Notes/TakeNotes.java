package com.rharshit.winddown.Notes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.rharshit.winddown.Notes.db.DBHandler;
import com.rharshit.winddown.R;
import com.rharshit.winddown.Util.Theme;

public class TakeNotes extends AppCompatActivity {

    private Context mContext;
    private String user;

    private EditText etTitle;
    private EditText etText;

    private boolean edit;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Theme.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_notes);
        mContext = this;

        etTitle = findViewById(R.id.et_notes_title);
        etText = findViewById(R.id.et_notes_text);

        Intent i = getIntent();
        user = i.getStringExtra("USER");
        edit = i.getIntExtra("EDIT", 0) == 1;
        if (edit) {
            String title = i.getStringExtra("TITLE");
            String text = i.getStringExtra("TEXT");
            id = i.getStringExtra("ID");
            etTitle.setText(title);
            etText.setText(text);
        }
    }

    public void save(View view) {
        String title = etTitle.getText().toString();
        String text = etText.getText().toString();
        boolean success = edit
                ? DBHandler.updateNote(title, text, id)
                : DBHandler.insertNote(title, text, user);
        if (success) {
            Toast.makeText(getApplicationContext(), "Note saved", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
