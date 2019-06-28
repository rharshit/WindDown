package com.rharshit.winddown.Music;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MusicAdapter extends BaseAdapter {

    private ArrayList<String[]> list;
    private Context mContext;

    public static Comparator<String[]> comparator = new Comparator<String[]>() {
        @Override
        public int compare(String[] a, String[] b) {
            return a[1].toLowerCase().compareTo(b[1].toLowerCase());
        }
    };

    public MusicAdapter(Context mContext, ArrayList<String[]> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String[] s = list.get(position);
        ItemMusic item = new ItemMusic(mContext, s[0], s[1], s[2], s[3]);
        return item;
    }

    public String[] getValues(int position) {
        return list.get(position);
    }

    public void add(String[] item) {
        int index = Collections.binarySearch(list, item, comparator);
        if (index < 0) {
            index = -(index + 1);
        }
        list.add(index, item);
    }
}
