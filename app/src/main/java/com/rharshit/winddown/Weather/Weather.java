package com.rharshit.winddown.Weather;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rharshit.winddown.R;
import com.rharshit.winddown.Util.Theme;
import com.rharshit.winddown.Weather.api.ApiHandler;
import com.rharshit.winddown.Weather.api.City;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Weather extends AppCompatActivity {

    private EditText etSearch;
    private ListView lvSearch;
    private ImageButton ibClear;

    private Context mContext;
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Theme.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        mContext = this;

        etSearch = findViewById(R.id.et_search_city);
        lvSearch = findViewById(R.id.list_search_cities);
        ibClear = findViewById(R.id.clear_search);

        ibClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSearch.setText("");
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.metaweather.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final ApiHandler handler = retrofit.create(ApiHandler.class);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String query = etSearch.getText().toString();
                Log.d(TAG, "afterTextChanged: City: " + query);

                if (query.equals("")) {
                    ibClear.setVisibility(View.INVISIBLE);
                } else {
                    ibClear.setVisibility(View.VISIBLE);
                    Call<List<City>> call = handler.getCities(query);
                    call.enqueue(new Callback<List<City>>() {
                        @Override
                        public void onResponse(Call<List<City>> call, Response<List<City>> response) {
                            List<City> cities = response.body();
                            lvSearch.setAdapter(new CityViewAdapter(mContext, cities));
                        }

                        @Override
                        public void onFailure(Call<List<City>> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Failed retrieving data",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                City city = (City) lvSearch.getAdapter().getItem(position);
                Intent i = new Intent(mContext, WeatherActivity.class);
                i.putExtra("woeid", city.woeid);
                i.putExtra("city", city.title);
                i.putExtra("lat_long", city.latt_long);
                startActivity(i);
            }
        });
    }
}
