package com.rharshit.winddown.Notes;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Theme.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_notes);
        mContext = this;

        user = getIntent().getStringExtra("USER");

        etTitle = findViewById(R.id.et_notes_title);
        etText = findViewById(R.id.et_notes_text);
    }

    public void save(View view) {
        String title = etTitle.getText().toString();
        String text = etText.getText().toString();
        boolean success = DBHandler.insertNote(title, text, user);
        if(success){
            Toast.makeText(mContext, "Note saved", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
