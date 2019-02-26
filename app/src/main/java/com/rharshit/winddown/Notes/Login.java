package com.rharshit.winddown.Notes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.rharshit.winddown.R;
import com.rharshit.winddown.Util.Theme;

public class Login extends AppCompatActivity {

    private EditText etUser;
    private EditText etPass;
    private Button bLogin;
    private TextView newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Theme.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUser = findViewById(R.id.notes_et_username);
        etPass = findViewById(R.id.notes_et_password);
        bLogin = findViewById(R.id.notes_b_login);
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("username", etUser.getText().toString());
                setResult(1, i);
                finish();
            }
        });
    }
}
