package com.rharshit.winddown.UI;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rharshit.winddown.R;

import java.util.Calendar;
import java.util.HashMap;

public class DateTime extends LinearLayout {

    HashMap<Integer, String> days = new HashMap<Integer, String>() {{
        put(2, "Mon");
        put(3, "Tue");
        put(4, "Wed");
        put(5, "Thu");
        put(6, "Fri");
        put(7, "Sat");
        put(1, "Sun");
    }};
    HashMap<Integer, String> months = new HashMap<Integer, String>() {{
        put(0, "Jan");
        put(1, "Feb");
        put(2, "Mar");
        put(3, "Apr");
        put(4, "May");
        put(5, "Jun");
        put(6, "Jul");
        put(7, "Aug");
        put(8, "Sep");
        put(9, "Oct");
        put(10, "Nov");
        put(11, "Dec");
    }};
    private String date;
    private String day;
    private String month;
    private TextView tvDay;

    public DateTime(Context context) {
        super(context);
        ViewGroup.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
        ((LayoutParams) params).gravity = Gravity.CENTER_HORIZONTAL;
        this.setLayoutParams(params);
        this.setOrientation(HORIZONTAL);

        int padding = (int) getResources().getDimension(R.dimen.date_time_padding);

        tvDay = new TextView(context);
        tvDay.setTextSize(20.0f);
        tvDay.setPadding(padding, 0, padding, 0);
        tvDay.setGravity(Gravity.CENTER_HORIZONTAL);

        this.addView(tvDay);

        update();
    }

    public void update() {
        Calendar c = Calendar.getInstance();

        date = "" + c.get(Calendar.DAY_OF_MONTH);
        day = days.get(c.get(Calendar.DAY_OF_WEEK));
        month = months.get(c.get(Calendar.MONTH));

        tvDay.setText(day + ", " + date + " " + month);
    }
}
