package com.rharshit.winddown.Contacts;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.rharshit.winddown.Gallery.Function;
import com.rharshit.winddown.Messages.NewSmsActivity;
import com.rharshit.winddown.Phone.Phone;
import com.rharshit.winddown.R;
import com.rharshit.winddown.Util.Theme;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class Contacts extends AppCompatActivity {
    static final int REQUEST_PERMISSION_KEY = 1;
    LinearLayout Load_Contacts;
    ListView list_contacts;
    LoadContacts loadContactTask;
    LoadContacts loadContactTask1;
    EditText search;
    ArrayList<Android_Contacts> arrayList =new ArrayList<Android_Contacts>(  );
    ArrayList<Android_Contacts> arrayList1 =new ArrayList<Android_Contacts>(  );
    private Context mContext;
    private String TAG = "Contacts";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Theme.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        mContext = this;

        list_contacts=(ListView)findViewById( R.id.list_contacts );
        search=(EditText)findViewById( R.id.search_name );
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String key = search.getText().toString().trim();
                if(key.isEmpty() || key == null)
                {
                    Contact_List_Adapter adapter=new Contact_List_Adapter( Contacts.this,arrayList );
                    list_contacts.setAdapter( adapter );
                }
                else {
                    Log.d(TAG, "onTextChanged: " + key);
                    arrayList1.clear();
                    for (int i=0;i<arrayList.size();i++)
                    {
                        if(arrayList.get( i ).contact_name.toLowerCase().contains( key.toLowerCase() )||arrayList.get( i ).contact_number.contains( (key) )) {
                            arrayList1.add( arrayList.get( i ) );
                        }
                    }

                    Contact_List_Adapter adapter1=new Contact_List_Adapter( Contacts.this,arrayList1 );
                    list_contacts.setAdapter( adapter1 );
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        String[] PERMISSIONS = {Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS};
        if(!Function.hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSION_KEY);


        }

}
    public class NameSorter implements Comparator<Android_Contacts>
    {
        @Override
        public int compare(Android_Contacts o1, Android_Contacts o2) {
            return o1.contact_name.compareToIgnoreCase(o2.contact_name);
        }
    }
class LoadContacts extends AsyncTask<String,Void,String>{
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        arrayList.clear();
    }

    @Override
    protected String doInBackground(String... strings) {
        String xml  = "";
        loadContacts();
        return xml;    }


    @Override
    protected void onPostExecute(String s) {
        Contact_List_Adapter adapter = new Contact_List_Adapter(Contacts.this, arrayList);
        list_contacts.setAdapter( adapter );
    }

}
    public void loadContacts()
    {

        ContentResolver contentResolver=getContentResolver();
        Cursor cursor=contentResolver.query( ContactsContract.Contacts.CONTENT_URI,null,null,null,null );
        if(cursor.getCount()>0)
        {
            while (cursor.moveToNext()) {
                Android_Contacts android_contacts=new Android_Contacts();
                android_contacts.contact_id= cursor.getString( cursor.getColumnIndex( ContactsContract.Contacts._ID ) );
                android_contacts.contact_name = cursor.getString( cursor.getColumnIndex( ContactsContract.Contacts.DISPLAY_NAME ) );
                int hasPhoneNumber = Integer.parseInt( cursor.getString( cursor.getColumnIndex( ContactsContract.Contacts.HAS_PHONE_NUMBER ) ) );
                if (hasPhoneNumber > 0) {
                    Cursor cursor2 = contentResolver.query( ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{android_contacts.contact_id}, null );
                    while (cursor2.moveToNext()) {
                        android_contacts.contact_number = cursor2.getString( cursor2.getColumnIndex( ContactsContract.CommonDataKinds.Phone.NUMBER ) );
                        //builder.append( "Contact:" ).append( name ).append( ",Number:" ).append( PhoneNumber ).append( "\n\n" );
                    }
                    cursor2.close();
                }
                if(android_contacts != null){
                    if(android_contacts.contact_name != null && !android_contacts.contact_name.isEmpty()
                            && android_contacts.contact_number != null && !android_contacts.contact_number.isEmpty()
                            && android_contacts.contact_id != null && !android_contacts.contact_id.isEmpty()){
                        arrayList.add( android_contacts );
                    }
                }
            }
        }
        cursor.close();
    }


    public class Android_Contacts{
        public String contact_name="";
        public String contact_number="";
        public String contact_id="";
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );
        switch (requestCode) {
            case REQUEST_PERMISSION_KEY: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadContactTask = new LoadContacts();
                    loadContactTask.execute();
                } else {
                    Toast.makeText( Contacts.this, "You must accept permissions.", Toast.LENGTH_LONG ).show();
                }
            }
        }
    }
    protected void onResume() {
        super.onResume();

        String[] PERMISSIONS = {Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS};
        if(!Function.hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSION_KEY);
        }else{
            loadContactTask = new LoadContacts();
            loadContactTask.execute();
        }


    }

        public class Contact_List_Adapter extends BaseAdapter{
        private Activity activity;
        private ArrayList<Android_Contacts> arrayList;
        public Contact_List_Adapter(Activity activity, ArrayList<Android_Contacts> arrayList) {
            this.activity=activity;
            this.arrayList=arrayList;
            arrayList.sort( new NameSorter() );
        }

        @Override
        public int getCount() {
           return  arrayList.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view=getLayoutInflater().inflate( R.layout.activity_contact_list,null );
            TextView name=(TextView)view.findViewById( R.id.contact_Name );
            name.setText( arrayList.get( i ).contact_name );
            final TextView Number =(TextView)view.findViewById( R.id.contact_Number );
            Number.setText( arrayList.get( i ).contact_number );

            ImageButton call=(ImageButton)view.findViewById( R.id.contacts_call );
            ImageButton mess=(ImageButton)view.findViewById( R.id.contacts_message );
            call.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent( Contacts.this, Phone.class );
                    intent.putExtra( "Number",arrayList.get(i).contact_number);
                    startActivity( intent );
                }
            } );
            mess.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in=new Intent( Contacts.this, NewSmsActivity.class );
                    in.putExtra( "Number",arrayList.get( i ).contact_number);
                    startActivity( in );

                }
            } );
            return view;
        }
    }

}
