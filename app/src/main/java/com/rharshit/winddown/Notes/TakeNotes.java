package com.rharshit.winddown.Notes;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rharshit.winddown.R;
import com.rharshit.winddown.Util.Theme;

public class TakeNotes extends AppCompatActivity {

    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Theme.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_notes);
        mContext = this;
    }
}
