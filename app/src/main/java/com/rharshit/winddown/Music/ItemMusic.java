package com.rharshit.winddown.Music;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rharshit.winddown.R;

public class ItemMusic extends LinearLayout {

    private String path;
    private String name;
    private String album;
    private String albumId;
    private TextView tvTitle;
    private TextView tvText;

    public ItemMusic(Context context, String path, String name, String album, String albumId) {
        super(context);

        this.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        this.path = path;
        this.name = name;
        this.album = album;
        this.albumId = albumId;

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout body = (LinearLayout) layoutInflater.inflate(R.layout.item_notes, null, false);

        tvTitle = body.findViewById(R.id.title);
        tvText = body.findViewById(R.id.text);

        tvTitle.setText(name);
        tvText.setText(album);

        this.addView(body);
    }
}
