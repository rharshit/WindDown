package com.rharshit.winddown.Phone;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rharshit.winddown.R;
import com.rharshit.winddown.Util.Theme;

import java.util.ArrayList;

public class Phone extends AppCompatActivity {
private static final int REQUEST_CALL=1;
//private EditText mEditTextNumber;
private TextView tvPhone;
private ImageButton f;
private ImageButton del;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Theme.getTheme());
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();

        setContentView(R.layout.activity_phone);
        tvPhone=findViewById( R.id.phone );
        tvPhone.setText( intent.getStringExtra( "Number" ) );
        f=findViewById( R.id.placecall );
        f.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall();
            }
        } );

        del = findViewById(R.id.del);
        del.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                tvPhone.setText("");
                Vibrator v1 = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v1.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    //deprecated in API 26
                    v1.vibrate(100);
                }
                return false;
            }
        });

    }

    private void makePhoneCall() {
        String number=tvPhone.getText().toString();
        if(number.trim().length()>0)
        {
            if(ContextCompat.checkSelfPermission( Phone.this, Manifest.permission.CALL_PHONE )!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions( Phone.this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL );
            }else
            {
                String dial="tel:"+number;
                startActivity( new Intent( Intent.ACTION_CALL, Uri.parse( dial ) ) );
            }
        }
        else {
            Toast.makeText( getApplicationContext(), "Enter Phone Number", Toast.LENGTH_SHORT ).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       if(requestCode== REQUEST_CALL){
           if(grantResults.length>0&& grantResults[0]==PackageManager.PERMISSION_GRANTED){
               makePhoneCall();
           }else{
              Toast.makeText( getApplicationContext(), "Permission DENIED", Toast.LENGTH_SHORT ).show();
           }
       }
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );
    }

    public void btnPress(View view) {
        if(view.getId() == R.id.del){
            String s = tvPhone.getText().toString();
            if(!s.equals("")){
                s = s.substring(0, s.length()-1);
                tvPhone.setText(s);
            }
        } else {
            Button b = (Button) view;
            String s = b.getText().toString();
            tvPhone.setText(tvPhone.getText() + s);
        }
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(50);
        }
    }
}
