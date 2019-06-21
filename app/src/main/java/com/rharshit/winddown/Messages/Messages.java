package com.rharshit.winddown.Messages;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lifeofcoding.cacheutlislibrary.CacheUtils;
import com.rharshit.winddown.R;
import com.rharshit.winddown.Util.Theme;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Messages extends AppCompatActivity {

    static final int REQUEST_PERMISSION_KEY = 1;
    static Messages inst;
    ArrayList<HashMap<String, String>> smsList = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> tmpList = new ArrayList<HashMap<String, String>>();
    LoadSms loadsmsTask;
    InboxAdapter adapter, tmpadapter;
    ListView listView;
    FloatingActionButton fab_new;
    ProgressBar loader;
    int i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Theme.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        CacheUtils.configureCache(this);

        listView = findViewById(R.id.listView);
        loader = findViewById(R.id.loader);
        fab_new = findViewById(R.id.fab_new);

        listView.setEmptyView(loader);


        fab_new.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Messages.this, NewSmsActivity.class));
            }
        });
    }

    public void init() {
        smsList.clear();
        try {
            tmpList = (ArrayList<HashMap<String, String>>) Function.readCachedFile(Messages.this, "smsapp");
            tmpadapter = new InboxAdapter(Messages.this, tmpList);
            listView.setAdapter(tmpadapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        final int position, long id) {
                    loadsmsTask.cancel(true);
                    Intent intent = new Intent(Messages.this, Chat.class);
                    intent.putExtra("name", tmpList.get(+position).get(Function.KEY_NAME));
                    intent.putExtra("address", tmpList.get(+position).get(Function.KEY_PHONE));
                    intent.putExtra("thread_id", tmpList.get(+position).get(Function.KEY_THREAD_ID));
                    startActivity(intent);
                }
            });
        } catch (Exception e) {
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_KEY: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    init();
                    loadsmsTask = new LoadSms();
                    loadsmsTask.execute();
                } else {
                    Toast.makeText(Messages.this, "You must accept permissions.", Toast.LENGTH_LONG).show();
                }
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        String[] PERMISSIONS = {Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS};
        if (!Function.hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSION_KEY);
        } else {
            init();
            loadsmsTask = new LoadSms();
            loadsmsTask.execute();
        }
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    class LoadSms extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            smsList.clear();
        }

        protected String doInBackground(String... args) {
            String xml = "";

            try {
                Uri uriInbox = Uri.parse("content://sms/inbox");

                Cursor inbox = getContentResolver().query(uriInbox, null, "address IS NOT NULL) GROUP BY (thread_id", null, null); // 2nd null = "address IS NOT NULL) GROUP BY (address"
                Uri uriSent = Uri.parse("content://sms/sent");
                Cursor sent = getContentResolver().query(uriSent, null, "address IS NOT NULL) GROUP BY (thread_id", null, null); // 2nd null = "address IS NOT NULL) GROUP BY (address"
                Cursor c = new MergeCursor(new Cursor[]{inbox, sent}); // Attaching inbox and sent sms


                if (c.moveToFirst()) {
                    for (int i = 0; i < c.getCount(); i++) {
                        String name = null;
                        String phone = "";
                        String _id = c.getString(c.getColumnIndexOrThrow("_id"));
                        String thread_id = c.getString(c.getColumnIndexOrThrow("thread_id"));
                        String msg = c.getString(c.getColumnIndexOrThrow("body"));
                        String type = c.getString(c.getColumnIndexOrThrow("type"));
                        String timestamp = c.getString(c.getColumnIndexOrThrow("date"));
                        phone = c.getString(c.getColumnIndexOrThrow("address"));


                        name = CacheUtils.readFile(thread_id);
                        if (name == null) {
                            name = Function.getContactbyPhoneNumber(getApplicationContext(), c.getString(c.getColumnIndexOrThrow("address")));
                            CacheUtils.writeFile(thread_id, name);
                        }


                        smsList.add(Function.mappingInbox(_id, thread_id, name, phone, msg, type, timestamp, Function.converToTime(timestamp)));
                        c.moveToNext();
                    }
                }
                c.close();

            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            Collections.sort(smsList, new MapComparator(Function.KEY_TIMESTAMP, "dsc")); // Arranging sms by timestamp decending
            ArrayList<HashMap<String, String>> purified = Function.removeDuplicates(smsList); // Removing duplicates from inbox & sent
            smsList.clear();
            smsList.addAll(purified);

            // Updating cache data
            try {
                Function.createCachedFile(Messages.this, "smsapp", smsList);
            } catch (Exception e) {
            }
            // Updating cache data

            return xml;
        }

        @Override
        protected void onPostExecute(String xml) {

            if (!tmpList.equals(smsList)) {
                adapter = new InboxAdapter(Messages.this, smsList);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            final int position, long id) {
                        Intent intent = new Intent(Messages.this, Chat.class);
                        intent.putExtra("name", smsList.get(+position).get(Function.KEY_NAME));
                        intent.putExtra("address", tmpList.get(+position).get(Function.KEY_PHONE));
                        intent.putExtra("thread_id", smsList.get(+position).get(Function.KEY_THREAD_ID));
                        startActivity(intent);
                    }
                });
            }


        }
    }

}


class InboxAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;

    public InboxAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data = d;
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        InboxViewHolder holder = null;
        if (convertView == null) {
            holder = new InboxViewHolder();
            convertView = LayoutInflater.from(activity).inflate(
                    R.layout.conversation_list_item, parent, false);

            holder.inbox_thumb = convertView.findViewById(R.id.inbox_thumb);
            holder.inbox_user = convertView.findViewById(R.id.inbox_user);
            holder.inbox_msg = convertView.findViewById(R.id.inbox_msg);
            holder.inbox_date = convertView.findViewById(R.id.inbox_date);

            convertView.setTag(holder);
        } else {
            holder = (InboxViewHolder) convertView.getTag();
        }
        holder.inbox_thumb.setId(position);
        holder.inbox_user.setId(position);
        holder.inbox_msg.setId(position);
        holder.inbox_date.setId(position);

        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);
        try {
            holder.inbox_user.setText(song.get(Function.KEY_NAME));
            holder.inbox_msg.setText(song.get(Function.KEY_MSG));
            holder.inbox_date.setText(song.get(Function.KEY_TIME));

            String firstLetter = String.valueOf(song.get(Function.KEY_NAME).charAt(0));
            ColorGenerator generator = ColorGenerator.MATERIAL;
            int color = generator.getColor(getItem(position));
            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(firstLetter, color);
            holder.inbox_thumb.setImageDrawable(drawable);
        } catch (Exception e) {
        }
        return convertView;
    }
}


class InboxViewHolder {
    ImageView inbox_thumb;
    TextView inbox_user, inbox_msg, inbox_date;
}

