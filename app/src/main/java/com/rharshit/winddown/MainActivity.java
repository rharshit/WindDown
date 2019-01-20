package com.rharshit.winddown;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.rharshit.winddown.Camera.Camera;
import com.rharshit.winddown.Contacts.Contacts;
import com.rharshit.winddown.Gallery.Gallery;
import com.rharshit.winddown.Messages.Messages;
import com.rharshit.winddown.Music.Music;
import com.rharshit.winddown.Phone.Phone;
import com.rharshit.winddown.UI.AppIcon;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";
    private Context mContext;

    private HorizontalScrollView hsView;
    private LinearLayout llScroll;

    private int vHeight;
    private int vWidth;

    private int nChild;

    private void init(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        vHeight = displayMetrics.heightPixels;
        vWidth = displayMetrics.widthPixels;

        hsView = findViewById(R.id.hsMainScrollView);
        llScroll = findViewById(R.id.llHorizintalScroll);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        init();
        populate();

        debug();
    }

    private void debug() {
        nChild = llScroll.getChildCount();
        Log.d(TAG, "width: " + vWidth);
        hsView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                for (int i = 0; i < nChild; i++) {
                    AppIcon tmp = (AppIcon) llScroll.getChildAt(i);
                    tmp.updatePos();
                }
            }
        });
    }

    private void populate(){
        llScroll.addView(
                new AppIcon(this, vWidth, vHeight,
                    getResources().getDrawable(R.drawable.ic_phone), "Phone", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext, Phone.class);
                    startActivity(i);
                }
            })
        );
        llScroll.addView(
                new AppIcon(this, vWidth, vHeight,
                        getResources().getDrawable(R.drawable.ic_contacts), "Contacts", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(mContext, Contacts.class);
                        startActivity(i);
                    }
                })
        );
        llScroll.addView(
                new AppIcon(this, vWidth, vHeight,
                        getResources().getDrawable(R.drawable.ic_message), "Messages", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(mContext, Messages.class);
                        startActivity(i);
                    }
                })
        );
        llScroll.addView(
                new AppIcon(this, vWidth, vHeight,
                        getResources().getDrawable(R.drawable.ic_camera), "Camera", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(mContext, Camera.class);
                        startActivity(i);
                    }
                })
        );
        llScroll.addView(
                new AppIcon(this, vWidth, vHeight,
                        getResources().getDrawable(R.drawable.ic_gallery), "Gallery", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(mContext, Gallery.class);
                        startActivity(i);
                    }
                })
        );
        llScroll.addView(
                new AppIcon(this, vWidth, vHeight,
                        getResources().getDrawable(R.drawable.ic_music), "Music", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(mContext, Music.class);
                        startActivity(i);
                    }
                })
        );
    }
}
