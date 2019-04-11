package com.rharshit.winddown.Notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.rharshit.winddown.R;

import java.util.ArrayList;

public class NotesAdapter extends BaseAdapter {

    private ArrayList<String[]> list;
    private Context mContext;

    public NotesAdapter(Context mContext, ArrayList<String[]> list){
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
        ItemNotes item = new ItemNotes(mContext, s[0], s[1], s[2]);
        return item;
    }

    public String[] getValues(int position){
        return list.get(position);
    }
}
