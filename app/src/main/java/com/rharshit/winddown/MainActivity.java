package com.rharshit.winddown;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.rharshit.winddown.Camera.Camera;
import com.rharshit.winddown.Contacts.Contacts;
import com.rharshit.winddown.Gallery.Gallery;
import com.rharshit.winddown.Messages.Messages;
import com.rharshit.winddown.Music.Music;
import com.rharshit.winddown.Notes.Login;
import com.rharshit.winddown.Phone.Phone;
import com.rharshit.winddown.UI.AppIconAdapter;
import com.rharshit.winddown.UI.AppIconData;
import com.rharshit.winddown.UI.DateTime;
import com.rharshit.winddown.UI.NotificationView;
import com.rharshit.winddown.Util.Notification;
import com.rharshit.winddown.Util.Theme;
import com.rharshit.winddown.Weather.Weather;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";
    private Context mContext;
    private TextView tvNotification;
    private NotificationReceiver nReceiver;
    private LinearLayout llWindDown;
    private AppIconAdapter appIconAdapter;

    private RecyclerView rvHorizontallApp;
    private LinearLayout llMainScroll;
    private LinearLayout llNotification;
    private GridLayout gvNotification;
    private TextView tvNotificationText;
    private ScrollView svMain;
    private LinearLayout llDateTime;

    private boolean showNotificaitons;

    private int vHeight;
    private int vWidth;
    private int nChild;

    private void init() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        vHeight = displayMetrics.heightPixels;
        vWidth = displayMetrics.widthPixels;

        rvHorizontallApp = findViewById(R.id.rv_horizontal_app_scroll);
        llMainScroll = findViewById(R.id.llVerticalScroll);
        llNotification = findViewById(R.id.llNotifivationView);
        gvNotification = findViewById(R.id.gvNotificaiton);
        tvNotificationText = findViewById(R.id.tvNotificaiton);
        svMain = findViewById(R.id.svMain);
        llDateTime = findViewById(R.id.llDateTime);

        showNotificaitons = true;

        int nIconWidth = (int) (getResources().getDimension(R.dimen.notification_icon_blur_dimen)
                + 2 * getResources().getInteger(R.integer.notification_icon_blur_radius));
        int llWidth = vWidth - ((int) (2 * (getResources().getDimension(R.dimen.notification_bg_padding)
                + getResources().getDimension(R.dimen.notification_bg_margin))));
        int nCol = llWidth / nIconWidth;
        gvNotification.setColumnCount(nCol);
//        Log.d(TAG, "init: " + nIconWidth + " " + llWidth + " " + nCol);

        llWindDown = findViewById(R.id.llWindDown);
        llWindDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Switching");
                Theme.switchTheme();
                for (int i = 0; i < gvNotification.getChildCount(); i++) {
                    NotificationView nv = (NotificationView) gvNotification.getChildAt(i);
                    nv.updateColor();
                }
                recreate();
            }
        });

        tvNotificationText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotificaitons = !showNotificaitons;
                for (int i = 0; i < gvNotification.getChildCount(); i++) {
                    View n = gvNotification.getChildAt(i);
                    n.setVisibility(showNotificaitons ? View.VISIBLE : View.GONE);
                }
            }
        });

        llDateTime.addView(new DateTime(mContext));
    }

    public void scrollUp(View view) {
        svMain.smoothScrollTo(0, 0);
    }

    private void notificationListener() {
        nReceiver = new NotificationReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.rharshit.winddown.NOTIFICATION_LISTENER");
        LocalBroadcastManager.getInstance(mContext).registerReceiver(nReceiver, filter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Theme.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        init();
        populate();
        notificationListener();
        getNotifications();
//        nChild = llScroll.getChildCount();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(mContext).unregisterReceiver(nReceiver);
    }

    private void getNotifications() {
        Intent i = new Intent("com.rharshit.winddown.NOTIFICATION_LISTENER_SERVICE");
        i.putExtra("EXTRA_ACTION", "getNotificaitons");
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(i);
    }


    private void populate() {
        appIconAdapter = new AppIconAdapter(getApps(), mContext);
        rvHorizontallApp.setAdapter(appIconAdapter);
        rvHorizontallApp.setLayoutManager(new LinearLayoutManager(
                mContext, LinearLayoutManager.HORIZONTAL, false));
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(rvHorizontallApp);
        rvHorizontallApp.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                for (int i = 0; i < recyclerView.getChildCount(); i++) {
                    ((AppIconAdapter) recyclerView.getAdapter())
                            .updateViews();
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private List<AppIconData> getApps() {
        List<AppIconData> apps = new ArrayList<>();
        apps.add(new AppIconData(getResources().getDrawable(R.drawable.ic_phone), "Phone", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(mContext, Phone.class);
                        startActivity(i);
                    }
                })
        );
        apps.add(new AppIconData(getResources().getDrawable(R.drawable.ic_contacts), "Contacts", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(mContext, Contacts.class);
                        startActivity(i);
                    }
                })
        );
        apps.add(new AppIconData(getResources().getDrawable(R.drawable.ic_message), "Messages", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(mContext, Messages.class);
                        startActivity(i);
                    }
                })
        );
        apps.add(new AppIconData(getResources().getDrawable(R.drawable.ic_notes), "Notes", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(mContext, Login.class);
                        startActivity(i);
                    }
                })
        );
        apps.add(new AppIconData(getResources().getDrawable(R.drawable.ic_camera), "Camera", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(mContext, Camera.class);
                        startActivity(i);
                    }
                })
        );
        apps.add(new AppIconData(getResources().getDrawable(R.drawable.ic_gallery), "Gallery", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(mContext, Gallery.class);
                        startActivity(i);
                    }
                })
        );
        apps.add(new AppIconData(getResources().getDrawable(R.drawable.ic_music), "Music", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(mContext, Music.class);
                        startActivity(i);
                    }
                })
        );
        apps.add(new AppIconData(getResources().getDrawable(R.drawable.ic_weather), "Weather", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(mContext, Weather.class);
                        startActivity(i);
                    }
                })
        );
        return apps;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addNotification(Notification n, String ticker) {
        for (int i = 0; i < gvNotification.getChildCount(); i++) {
            NotificationView nv = (NotificationView) gvNotification.getChildAt(i);
            if (n.getPackageName().equals(nv.getPackageName())) {
//                Log.d(TAG, "Update notification: " + n.getPackageName() + " GroupKey: " + n.getGroupKey()
//                        + " Key: " + n.getKey() + " nGroup: " + n.getGroup() + " ID: " + n.getId()
//                        + " Channel ID: " + n.getChannelId());
                nv.updateNotification(n, ticker);
                return;
            }
        }
//        Log.d(TAG, "New notification: " + n.getPackageName() + " GroupKey: " + n.getGroupKey()
//                + " Key: " + n.getKey() + " nGroup: " + n.getGroup() + " ID: " + n.getId()
//                + " Channel ID: " + n.getChannelId());
        Drawable icon = null;
        try {
            icon = getPackageManager().getApplicationIcon(n.getPackageName());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        ApplicationInfo appInfo = null;
        try {
            appInfo = getPackageManager().getApplicationInfo(n.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String appName = appInfo == null ?
                n.getPackageName() :
                getPackageManager().getApplicationLabel(appInfo).toString();
        NotificationView notificationView = new NotificationView(mContext, n, icon, appName, ticker);
        notificationView.setVisibility(showNotificaitons ? View.VISIBLE : View.GONE);
        gvNotification.addView(notificationView, gvNotification.getChildCount());
//        notificationView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                NotificationView nv = ((NotificationView)v);
//                showNotifications(nv);
//            }
//        });
    }

//    private void showNotifications(NotificationView nv) {
//        ArrayList<String> notifications = nv.getNotifications();
//        String msg = "";
//        for (String notification : notifications) {
//            msg += "\n" + notification + "\n";
//        }
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                dialog.cancel();
//            }
//        })
//                .setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        //  Action for 'NO' Button
//                        dialog.cancel();
//                        Toast.makeText(getApplicationContext(), "Dismissed",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//        AlertDialog alert = builder.create();
//        alert.setTitle(nv.getAppName());
//        alert.setMessage(msg);
//        alert.setIcon(nv.getIcon());
//        alert.show();
//    }

    private void removeNotification(Notification n, String ticker) {
        for (int i = 0; i < gvNotification.getChildCount(); i++) {
            NotificationView nv = (NotificationView) gvNotification.getChildAt(i);
            if (n.getPackageName().equals(nv.getPackageName())) {
//                Log.d(TAG, "Remove notification: " + n.getPackageName() + " GroupKey: " + n.getGroupKey()
//                        + " Key: " + n.getKey() + " nGroup: " + n.getGroup() + " ID: " + n.getId()
//                        + " Channel ID: " + n.getChannelId());
                boolean remove = nv.removeNotification(n, ticker);
                if (remove) {
                    gvNotification.removeViewAt(i);
                }
                return;
            }
        }
    }

    class NotificationReceiver extends BroadcastReceiver {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            Notification n = bundle.getParcelable("NOTIFICATION");
            int action = intent.getIntExtra("ACTION", 3);
            String ticker;
            switch (action) {
                case 0:
//                    Log.d(TAG, "onReceive: Notificaiton List");
//                    listNotification(n, ticker);
//                    break;
                case 1:
//                    Log.d(TAG, "onReceive: Notificaiton Add");
                    ticker = intent.getStringExtra("TICKER_TEXT");
                    addNotification(n, ticker);
                    break;
                case 2:
//                    Log.d(TAG, "onReceive: Notificaiton Remove");
                    ticker = intent.getStringExtra("TICKER_TEXT");
                    removeNotification(n, ticker);
                    break;
                case 3:
//                    Log.e(TAG, "onReceive: Notification intent action" );
                    break;
            }
        }
    }
}
