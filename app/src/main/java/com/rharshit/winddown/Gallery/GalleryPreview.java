package com.rharshit.winddown.Gallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

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
        //getSupportActionBar().hide();
        setContentView(R.layout.gallery_preview);
        Intent intent = getIntent();
        path = intent.getStringExtra("path");
        GalleryPreviewImg = (ImageView) findViewById(R.id.GalleryPreviewImg);
        Glide.with(GalleryPreview.this)
                .load(new File(path)) // Uri of the picture
                .into(GalleryPreviewImg);
    }
}