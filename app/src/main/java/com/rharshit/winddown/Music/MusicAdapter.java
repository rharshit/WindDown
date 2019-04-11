package com.rharshit.winddown.Music;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class MusicAdapter extends BaseAdapter {

    private ArrayList<String[]> list;
    private Context mContext;

    public MusicAdapter(Context mContext, ArrayList<String[]> list){
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
        ItemMusic item = new ItemMusic(mContext, s[0], s[1], s[2]);
        return item;
    }

    public String[] getValues(int position){
        return list.get(position);
    }
}
