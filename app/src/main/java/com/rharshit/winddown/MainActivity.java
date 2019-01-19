package com.rharshit.winddown;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private HorizontalScrollView hsView;
    private LinearLayout llScroll;

    private void init(){
        hsView = (HorizontalScrollView) findViewById(R.id.hsMainScrollView);
        llScroll = (LinearLayout) findViewById(R.id.llHorizintalScroll);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        init();
        populate();
    }

    private void populate(){
        for (int i =0; i<10; i++){
            TextView tmp = new TextView(this);
            tmp.setText("Hello World!");
            tmp.setTextSize(40.0f);

            llScroll.addView(tmp);
        }
    }
}
