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
    public static final String KEY_EMPRESA = "empresa";
    public static final String KEY_NUMERO = "ruta";
    public static final String KEY_CREATION_DATE = "creation_date";
    private SQLiteDatabase db;
    private final Context context;
    public RutaDBAdapter(Context _context) {
        this.context = _context;
    }

    private static class RutaDBOpenHelper extends SQLiteOpenHelper {
        public RutaDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }
        // SQL Statement to create a new database.
        private static final String DATABASE_CREATE = "create table "
                + DATABASE_TABLE + " (" + KEY_ID
                + " integer primary key autoincrement, " + KEY_EMPRESA
                + " text not null, " + KEY_CREATION_DATE + " long);";
        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(DATABASE_CREATE);
        }
        @Override
        public void onUpgrade(SQLiteDatabase _db, int _oldVersion,
                              int _newVersion) {
            Log.w("TaskDBAdapter", "Upgrading from version " + _oldVersion
                    + " to " + _newVersion
                    + ", which will destroy all old data");
            // Drop the old table.
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            // Create a new one.
            onCreate(_db);
        }
    }
}
