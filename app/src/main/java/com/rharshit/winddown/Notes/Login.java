package com.rharshit.winddown.Notes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rharshit.winddown.Notes.db.DBHandler;
import com.rharshit.winddown.R;
import com.rharshit.winddown.Util.Theme;

public class Login extends AppCompatActivity {

    private static final String TAG = "Notes/Login";
    private EditText etUser;
    private EditText etPass;
    private Button bLogin;
    private TextView newUser;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Theme.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;

        initDB();

        etUser = findViewById(R.id.notes_login_et_username);
        etPass = findViewById(R.id.notes_login_et_password);
        bLogin = findViewById(R.id.notes_b_login);
        newUser = findViewById(R.id.notes_login_tv_new_user);

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u = etUser.getText().toString();
                String p = etPass.getText().toString();
                boolean res = DBHandler.getUser(u, p);
                if (res) {
                    Intent i = new Intent(mContext, Notes.class);
                    i.putExtra("username", etUser.getText().toString());
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid username/password", Toast.LENGTH_LONG)
                            .show();
                }

            }
        });

        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, Signup.class);
                startActivityForResult(i, 0);
            }
        });
    }

    private void initDB() {
        new DBHandler(mContext);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) { //success
            String username = data.getStringExtra("username");
            Intent i = new Intent(mContext, Notes.class);
            i.putExtra("username", username);
            Log.d(TAG, "onActivityResult: " + username);
            startActivity(i);
            finish();
        }
    }
}
