package com.rharshit.winddown.Notes.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class DBHandler extends SQLiteOpenHelper {

    public static final String DB_NAME = "notes.db";
    // Table names
    public static final String TABLE_USERS = "USERS";
    public static final String TABLE_NOTES = "NOTES";
    // Column names
    public static final String USERS_USERNAME = "USERNAME";
    public static final String USERS_PASSWORD = "PASSWORD";
    public static final String NOTES_USERNAME = "USERNAME";
    public static final String NOTES_TITLE = "TITLE";
    public static final String NOTES_TEXT = "TEXT";
    private static SQLiteDatabase db;

    public DBHandler(Context context) {
        super(context, DB_NAME, null, 1);
        db = this.getWritableDatabase();
    }

    public static boolean insertUser(String username, String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERS_USERNAME, username);
        contentValues.put(USERS_PASSWORD, password);
        long result = db.insert(TABLE_USERS, null, contentValues);
        return result != -1;
    }

    public static boolean insertNote(String title, String text, String user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTES_TITLE, title);
        contentValues.put(NOTES_TEXT, text);
        contentValues.put(NOTES_USERNAME, user);
        long result = db.insert(TABLE_NOTES, null, contentValues);
        return result != -1;
    }

    public static boolean updateNote(String title, String text, String id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTES_TITLE, title);
        contentValues.put(NOTES_TEXT, text);
        long result = db.update(TABLE_NOTES, contentValues, "ID=" + id, null);
        return result != -1;
    }

    public static boolean getUser(String username, String password) {
        Cursor users = getAllUsers();
        while (users.moveToNext()) {
            Log.d(TAG, "getUser: " + users.getString(0) + " " + users.getString(1));
            if (users.getString(0).equals(username)) {
                return users.getString(1).equals(password);
            }
        }
        return false;
    }

    public static Cursor getAllUsers() {
        return db.rawQuery("SELECT * FROM " +
                TABLE_USERS, null);
    }

    public static Cursor getNotes(String user) {
        String[] where = new String[]{user};
        return db.rawQuery("SELECT " + NOTES_TITLE + ", " +
                NOTES_TEXT + ", ID FROM NOTES WHERE " +
                NOTES_USERNAME + " = ?", where);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query;

        query = "create table " + TABLE_NOTES +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NOTES_USERNAME + " TEXT, " +
                NOTES_TITLE + " TEXT, " +
                NOTES_TEXT + " TEXT)";
        Log.d(TAG, "onCreate: " + query);
        db.execSQL(query);

        query = "create table " + TABLE_USERS + " (" +
                USERS_USERNAME + " TEXT PRIMARY KEY, " +
                USERS_PASSWORD + " TEXT)";
        Log.d(TAG, "onCreate: " + query);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
    }
}
