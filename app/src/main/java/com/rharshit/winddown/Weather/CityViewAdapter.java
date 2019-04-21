package com.rharshit.winddown.Weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rharshit.winddown.R;
import com.rharshit.winddown.Weather.api.City;

import java.util.List;


public class CityViewAdapter extends BaseAdapter {

    private List<City> cities;
    private Context mContext;

    public CityViewAdapter(Context mContext, List<City> cities) {
        this.mContext = mContext;
        this.cities = cities;
    }

    @Override
    public int getCount() {
        return cities.size();
    }

    @Override
    public Object getItem(int position) {
        return cities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        City c = cities.get(position);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View item = inflater.inflate(R.layout.list_item_city, null, true);

        TextView tvCity = item.findViewById(R.id.city);
        TextView tvLatLong = item.findViewById(R.id.latlong);

        tvCity.setText(c.title);
        tvLatLong.setText(c.latt_long);

        return item;
    }
}
