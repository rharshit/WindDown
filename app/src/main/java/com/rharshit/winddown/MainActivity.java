package com.rharshit.winddown;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rharshit.winddown.UI.AppIcon;

public class MainActivity extends AppCompatActivity {

    private HorizontalScrollView hsView;
    private LinearLayout llScroll;

    private int vHeight;
    private int vWidth;

    private void init(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        vHeight = displayMetrics.heightPixels;
        vWidth = displayMetrics.widthPixels;

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
        for (int i =0; i<4; i++){
            llScroll.addView(new AppIcon(this, vWidth/2, vHeight/2));
        }
    }
}
