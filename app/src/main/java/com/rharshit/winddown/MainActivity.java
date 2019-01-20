package com.rharshit.winddown;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.rharshit.winddown.Phone.Phone;
import com.rharshit.winddown.UI.AppIcon;

public class MainActivity extends AppCompatActivity {

    private Context mCOntext;

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

        mCOntext = this;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        init();
        populate();
    }

    private void populate(){
        for (int i =0; i<4; i++){
            AppIcon tmp = new AppIcon(this, (int) (vWidth*0.75), (int) (vHeight*0.75),
                    getResources().getDrawable(R.drawable.ic_phone), "Phone", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mCOntext, Phone.class);
                    startActivity(i);
                }
            });
            llScroll.addView(tmp);
        }
    }
}
