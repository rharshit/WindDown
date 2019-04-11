package com.rharshit.winddown.Music;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.rharshit.winddown.R;
import com.rharshit.winddown.Util.Theme;

import java.util.ArrayList;

public class MusicList extends AppCompatActivity {

    private static final String TAG = "MusicList";
    private Context mContext;
    ArrayList<String[]> list;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Theme.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);
        mContext = this;

        setResult(0);

        lv = findViewById(R.id.list_music);

        list = new ArrayList<>();
        getSongList();
        populate();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String[] s = ((MusicAdapter)lv.getAdapter()).getValues(position);
                Intent i = new Intent();
                i.putExtra("URI", s[0]);
                i.putExtra("NAME", s[1]);
                i.putExtra("ALBUM", s[2]);
                i.putExtra("ID", s[3]);
                setResult(1, i);
                finish();
            }
        });
    }

    private void populate() {
        MusicAdapter adapter = new MusicAdapter(mContext, list);
        lv.setAdapter(adapter);
    }

    private void getSongList() {
        ContentResolver cr = mContext.getContentResolver();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        Cursor cur = cr.query(uri, null, selection, null, sortOrder);
        int count = 0;

        if(cur != null)
        {
            count = cur.getCount();

            if(count > 0)
            {
                while(cur.moveToNext())
                {
                    String path = cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.DATA));
                    String name = cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    String album = cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                    String id = cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                    Log.d(TAG, "getSongList: " + path + " " + name + " " + album + " " + id);
                    String[] s = new String[] {path, name, album, id};
                    list.add(s);
                }

            }
        }

        cur.close();
    }
}