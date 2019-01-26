package com.rharshit.winddown.Camera;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rharshit.winddown.R;
import com.rharshit.winddown.Util.Theme;

public class Camera extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Theme.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
    }
}
