package com.rharshit.winddown;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;

public class MainActivity extends AppCompatActivity {

    HorizontalScrollView hsView;

    void init(){
        hsView = (HorizontalScrollView) findViewById(R.id.hsMainScrollView);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        init();
    }
}
