package com.rharshit.winddown.Util;

import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import android.service.notification.StatusBarNotification;
import android.support.annotation.RequiresApi;

public class Notification implements Parcelable {

    public static final Creator<Notification> CREATOR = new Creator<Notification>() {
        @Override
        public Notification createFromParcel(Parcel in) {
            return new Notification(in);
        }

        @Override
        public Notification[] newArray(int size) {
            return new Notification[size];
        }
    };
    private final String groupKey;
    private final int id;
    private final String key;
    private final String overrideGroupKey;
    private final String packageName;
    private final long postTime;
    private final String tag;
    private final UserHandle user;
    private final int userId;
    private final boolean clearable;
    private final boolean group;
    private final boolean ongoing;
    private final int nBadgeIconType;
    private final String nChannelId;
    private final String nGroup;
    private final Icon nLargeIcon;
    private final String nShortcutId;
    private final Icon nSmallIcon;
    private final String nSortKey;
    private final long nTimeoutAfter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification(StatusBarNotification in) {
        groupKey = in.getGroupKey();
        id = in.getId();
        key = in.getKey();
        overrideGroupKey = in.getOverrideGroupKey();
        packageName = in.getPackageName();
        postTime = in.getPostTime();
        tag = in.getTag();
        user = in.getUser();
        userId = in.getUserId();
        clearable = in.isClearable();
        group = in.isGroup();
        ongoing = in.isOngoing();

        nBadgeIconType = in.getNotification().getBadgeIconType();
        nChannelId = in.getNotification().getChannelId();
        nGroup = in.getNotification().getGroup();
        nLargeIcon = in.getNotification().getLargeIcon();
        nShortcutId = in.getNotification().getShortcutId();
        nSmallIcon = in.getNotification().getSmallIcon();
        nSortKey = in.getNotification().getSortKey();
        nTimeoutAfter = in.getNotification().getTimeoutAfter();
    }

    protected Notification(Parcel in) {
        groupKey = in.readString();
        id = in.readInt();
        key = in.readString();
        overrideGroupKey = in.readString();
        packageName = in.readString();
        postTime = in.readLong();
        tag = in.readString();
        user = in.readParcelable(UserHandle.class.getClassLoader());
        userId = in.readInt();
        clearable = in.readByte() != 0;
        group = in.readByte() != 0;
        ongoing = in.readByte() != 0;
        nBadgeIconType = in.readInt();
        nChannelId = in.readString();
        nGroup = in.readString();
        nLargeIcon = in.readParcelable(Icon.class.getClassLoader());
        nShortcutId = in.readString();
        nSmallIcon = in.readParcelable(Icon.class.getClassLoader());
        nSortKey = in.readString();
        nTimeoutAfter = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(groupKey);
        dest.writeInt(id);
        dest.writeString(key);
        dest.writeString(overrideGroupKey);
        dest.writeString(packageName);
        dest.writeLong(postTime);
        dest.writeString(tag);
        dest.writeParcelable(user, flags);
        dest.writeInt(userId);
        dest.writeByte((byte) (clearable ? 1 : 0));
        dest.writeByte((byte) (group ? 1 : 0));
        dest.writeByte((byte) (ongoing ? 1 : 0));
        dest.writeInt(nBadgeIconType);
        dest.writeString(nChannelId);
        dest.writeString(nGroup);
        dest.writeParcelable(nLargeIcon, flags);
        dest.writeString(nShortcutId);
        dest.writeParcelable(nSmallIcon, flags);
        dest.writeString(nSortKey);
        dest.writeLong(nTimeoutAfter);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getPackageName() {
        return packageName;
    }

    public boolean isOngoing() {
        return ongoing;
    }
}
