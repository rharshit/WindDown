package com.rharshit.winddown.Gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.rharshit.winddown.R;
import com.rharshit.winddown.Util.Theme;

import java.io.File;

public class GalleryPreview extends AppCompatActivity {

    ImageView GalleryPreviewImg;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Theme.getTheme());
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        //getSupportActionBar().hide();
        setContentView(R.layout.gallery_preview);
        Intent intent = getIntent();
        path = intent.getStringExtra("path");
        GalleryPreviewImg = findViewById(R.id.GalleryPreviewImg);
        Glide.with(GalleryPreview.this)
                .load(new File(path)) // Uri of the picture
                .into(GalleryPreviewImg);
    }
}