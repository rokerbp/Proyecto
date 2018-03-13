package com.aplimovil.infobus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class RutaDBAdapter {
    private static final String DATABASE_NAME = "infoBus.db";
    private static final String DATABASE_TABLE = "infoRuta";
    private static final int DATABASE_VERSION = 1;
    public static final String KEY_ID = "_id";
    public static final String KEY_TASK = "task";
    public static final String KEY_CREATION_DATE = "creation_date";|
    private SQLiteDatabase db;
    private final Context context;
    public RutaDBAdapter(Context _context) {
        this.context = _context;
    }
}
