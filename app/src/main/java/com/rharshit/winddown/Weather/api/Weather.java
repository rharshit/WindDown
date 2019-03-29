package com.rharshit.winddown.Weather.api;

import java.util.HashMap;
import java.util.Map;

public class Weather {
    public long id;
    public String weather_state_name;
    public String weather_state_abbr;
    public String wind_direction_compass;
    public Float min_temp;
    public Float max_temp;
    public Float the_temp;
    public Float wind_speed;
    public Float wind_direction;
    public Float air_pressure;
    public int humidity;
    public Float visibility;
    public int predictability;

    public static Map<String, String> icons = new HashMap<String, String>() {{
        put("sn", "❄️");
        put("sl", "\uD83C\uDF28️");
        put("h", "☄️");
        put("t", "\uD83C\uDF29️");
        put("hr", "️\uD83C\uDF27️");
        put("lr", "\uD83C\uDF27️");
        put("s", "\uD83C\uDF26️");
        put("hc", "☁️️");
        put("lc", "\uD83C\uDF25️️");
        put("c", "\uD83C\uDF24️");
    }};
}
