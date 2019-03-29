package com.rharshit.winddown.Weather;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.rharshit.winddown.R;
import com.rharshit.winddown.Util.Theme;
import com.rharshit.winddown.Weather.api.ApiHandler;
import com.rharshit.winddown.Weather.api.WeatherInfo;
import com.rharshit.winddown.Weather.api.Weather;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherActivity extends AppCompatActivity {

    private static final String TAG = "WeatherActivity";
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Theme.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_show);
        mContext = this;

        Intent i = getIntent();
        int woeid = i.getIntExtra("woeid", 0);
        String city = i.getStringExtra("city");
        String lat_long = i.getStringExtra("lat_long");
        Log.d(TAG, "onCreate: woeid: " + woeid);

        ((TextView) findViewById(R.id.city_name)).setText(city);
        ((TextView) findViewById(R.id.lat_long)).setText(lat_long);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.metaweather.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final ApiHandler handler = retrofit.create(ApiHandler.class);

        Call<WeatherInfo> call = handler.getWeather(woeid);
        call.enqueue(new Callback<WeatherInfo>() {
            @Override
            public void onResponse(Call<WeatherInfo> call, Response<WeatherInfo> response) {
                WeatherInfo info = response.body();

                ((TextView) findViewById(R.id.weather_state_name)).setText(
                        info.consolidated_weather.get(0).weather_state_name
                );
                ((TextView) findViewById(R.id.weather_state_abbr)).setText(
                        Weather.icons.get(info.consolidated_weather.get(0).weather_state_abbr)
                );
                ((TextView) findViewById(R.id.the_temp)).setText(
                        String.format("%.2f°", info.consolidated_weather.get(0).the_temp)
                );
                ((TextView) findViewById(R.id.humidity)).setText(
                        String.format("Humidity\n%d", info.consolidated_weather.get(0).humidity)
                );
            }

            @Override
            public void onFailure(Call<WeatherInfo> call, Throwable t) {
                Toast.makeText(mContext, "Failed retrieving weather",
                        Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}
