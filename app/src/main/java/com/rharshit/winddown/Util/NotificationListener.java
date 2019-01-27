package com.rharshit.winddown.Util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class NotificationListener extends NotificationListenerService {
    private String TAG = this.getClass().getSimpleName();
    private NotificationListenerReceiver notificationListenerReciver;

    private Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        notificationListenerReciver = new NotificationListenerReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.rharshit.winddown.NOTIFICATION_LISTENER_SERVICE");
        LocalBroadcastManager.getInstance(mContext).registerReceiver(notificationListenerReciver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(mContext).unregisterReceiver(notificationListenerReciver);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {

        Log.i(TAG, "onNotificationPosted");
        Log.i(TAG, "ID :" + sbn.getId() + "\t" + sbn.getNotification().tickerText + "\t" + sbn.getPackageName());
        Intent i = new Intent("com.rharshit.winddown.NOTIFICATION_LISTENER");
        Notification n = new Notification(sbn);
        Bundle bundle = new Bundle();
        bundle.putParcelable("NOTIFICATION", n);
        i.putExtras(bundle);
        i.putExtra("ACTION", 1);
        CharSequence csTicker = sbn.getNotification().tickerText;
        i.putExtra("TICKER_TEXT", csTicker==null? "null" : csTicker.toString());
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(i);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i(TAG, "onNOtificationRemoved");
        Log.i(TAG, "ID :" + sbn.getId() + "\t" + sbn.getNotification().tickerText + "\t" + sbn.getPackageName());
        Intent i = new Intent("com.rharshit.winddown.NOTIFICATION_LISTENER");
        Notification n = new Notification(sbn);
        Bundle bundle = new Bundle();
        bundle.putParcelable("NOTIFICATION", n);
        i.putExtras(bundle);
        i.putExtra("ACTION", 2);
        CharSequence csTicker = sbn.getNotification().tickerText;
        i.putExtra("TICKER_TEXT", csTicker==null? "null" : csTicker.toString());
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(i);
    }

    class NotificationListenerReceiver extends BroadcastReceiver {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getStringExtra("EXTRA_ACTION").equals("getNotificaitons")) {
                for (StatusBarNotification sbn : NotificationListener.this.getActiveNotifications()) {
                    Log.i(TAG, "onNotificationListed");
                    Intent i = new Intent("com.rharshit.winddown.NOTIFICATION_LISTENER");
                    Notification n = new Notification(sbn);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("NOTIFICATION", n);
                    i.putExtras(bundle);
                    i.putExtra("ACTION", 0);
                    CharSequence csTicker = sbn.getNotification().tickerText;
                    i.putExtra("TICKER_TEXT", csTicker==null? "null" : csTicker.toString());
                    LocalBroadcastManager.getInstance(mContext).sendBroadcast(i);
                    Log.i(TAG, "ID :" + sbn.getId() + "\t" + sbn.getNotification().tickerText + "\t" + n.getPackageName());
                }
            }
        }
    }
}
