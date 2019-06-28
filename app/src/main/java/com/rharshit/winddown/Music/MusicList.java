package com.rharshit.winddown.Music;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.rharshit.winddown.R;
import com.rharshit.winddown.Util.Theme;

import java.util.ArrayList;

public class MusicList extends AppCompatActivity {

    private static final String TAG = "MusicList";

    static MusicAdapter adapter;
    //    static ArrayList<String[]> list;
    ListView lv;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Theme.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        mContext = this;

        setResult(0);

        lv = findViewById(R.id.list_music);
        if (adapter == null) {
            adapter = new MusicAdapter(mContext, new ArrayList<String[]>());
        }
        lv.setAdapter(adapter);

        if (adapter.isEmpty()) {
            Log.d(TAG, "onCreate: empty adapter");
            new GetSongListAsync().execute(adapter);
        }
        populate();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String[] s = ((MusicAdapter) lv.getAdapter()).getValues(position);
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
        lv.setAdapter(adapter);
    }

    class GetSongListAsync extends AsyncTask<MusicAdapter, Void, Void> {

        @Override
        protected Void doInBackground(MusicAdapter... musicAdapters) {
            final MusicAdapter musicAdapter = musicAdapters[0];

            ContentResolver cr = mContext.getContentResolver();

            Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
            String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
            Cursor cur = cr.query(uri, null, selection, null, sortOrder);
            int count = 0;

            if (cur != null) {
                count = cur.getCount();

                if (count > 0) {
                    while (cur.moveToNext()) {
                        String path = cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.DATA));
                        String name = cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                        String album = cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                        String id = cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));

                        Cursor cursor = getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                                new String[]{MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                                MediaStore.Audio.Albums._ID + "=?", new String[]{String.valueOf(id)},
                                null);

                        if (cursor.moveToFirst()) {
                            boolean albumArt = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART)) != null;

                            if (!albumArt) {
                                continue;
                            }
                        }
                        Log.d(TAG, "getSongList: " + path + " " + name + " " + album + " " + id);
                        while (name.contains(".mp3") && name.length() > 4) {
                            name = name.substring(0, name.length() - 4);
                        }
                        while (name.length() > 0) {
                            char x = name.charAt(0);
                            if (!((x >= 'a' && x <= 'z') || (x >= 'A' && x <= 'Z'))) {
                                name = name.substring(1);
                            } else {
                                break;
                            }
                        }
                        if (name.length() == 0) {
                            Log.d(TAG, "getSongList: name length: " + name.length());
                            continue;
                        }
                        final String[] s = new String[]{path, name, album, id};
                        MusicList.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                musicAdapter.add(s);
                                musicAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
            }

            cur.close();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
