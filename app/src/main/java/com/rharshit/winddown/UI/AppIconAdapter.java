package com.rharshit.winddown.UI;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rharshit.winddown.R;

import java.util.List;

public class AppIconAdapter extends RecyclerView.Adapter<AppIconViewHolder> {

    List<AppIconData> apps;
    AppIconViewHolder[] appViewHolder;
    Context mContext;

    public AppIconAdapter(List<AppIconData> apps, Context context){
        this.apps = apps;
        this.mContext = context;
        appViewHolder = new AppIconViewHolder[apps.size()];
    }

    @NonNull
    @Override
    public AppIconViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View appIconView = inflater.inflate(R.layout.app_icon, viewGroup, false);
        AppIconViewHolder viewHolder = new AppIconViewHolder(appIconView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AppIconViewHolder appIconViewHolder, int i) {
        appIconViewHolder.appIcon.setImageDrawable(apps.get(i).icon);
        appIconViewHolder.tvAppName.setText(apps.get(i).name);
        appIconViewHolder.itemView.setOnClickListener(apps.get(i).onClick);
        appViewHolder[i] = appIconViewHolder;
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void updateViews() {
        for (AppIconViewHolder view : appViewHolder) {
            if (view != null) {
                view.updateView();
            }
        }
    }
}
