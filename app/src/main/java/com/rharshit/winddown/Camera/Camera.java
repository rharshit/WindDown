package com.rharshit.winddown.Camera;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rharshit.winddown.R;

public class Camera extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_camera );

        if (null == savedInstanceState) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, Camera2BasicFragment.newInstance())
                    .commit();
        }
    }
}
