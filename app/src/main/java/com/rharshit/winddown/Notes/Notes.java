package com.rharshit.winddown.Notes;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rharshit.winddown.Notes.db.DBHandler;
import com.rharshit.winddown.R;
import com.rharshit.winddown.Util.Theme;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;

public class Notes extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "Notes/Notes";
    private String username;

    private TextView tvUsername;
    private ListView lvNotes;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Theme.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mContext = this;

        lvNotes = findViewById(R.id.notes_list);

        Intent i = getIntent();
        username = i.getStringExtra("username");
        Log.d(TAG, "onCreate: "+username);

        populateList();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, TakeNotes.class);
                i.putExtra("USER", username);
                startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateList();
    }

    private void populateList() {
        NotesAdapter adapter = new NotesAdapter(mContext, getNotes());
        lvNotes.setAdapter(adapter);
        lvNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String[] val = ((NotesAdapter)lvNotes.getAdapter()).getValues(position);
                Intent i = new Intent(mContext, TakeNotes.class);
                i.putExtra("USER", username);
                i.putExtra("EDIT", 1);
                i.putExtra("TITLE", val[0]);
                i.putExtra("TEXT", val[1]);
                i.putExtra("ID", val[2]);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.notes, menu);
        tvUsername = findViewById(R.id.notes_nav_header_title);
        tvUsername.setText(username);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private ArrayList<String[]> getNotes(){
        ArrayList<String[]> list = new ArrayList<>();
        Cursor c = DBHandler.getNotes(username);
        while (c.moveToNext()){
            String[] s = new String[]{
                    c.getString(0),
                    c.getString(1),
                    c.getString(2)
            };
            list.add(s);
        }
        return list;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}
