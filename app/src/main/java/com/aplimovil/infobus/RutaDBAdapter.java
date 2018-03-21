package com.aplimovil.infobus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Date;

public class RutaDBAdapter {
    private static final String DATABASE_NAME = "infoBus.db";
    private static final String DATABASE_TABLE = "infoRuta";
    private static final int DATABASE_VERSION = 1;
    public static final String KEY_ID = "_id";
    public static final String KEY_EMPRESA = "empresa";
    //public static final String KEY_NUMERO = "ruta";
    public static final String KEY_NUMERO = "numero";
    public static final String KEY_TIEMPO = "tiempo";
    public static final String KEY_LUGARES = "lugares";
    // agregar ruta pero de array
    // public static final String KEY_POE = "tiempo";
    public static final String KEY_CREATION_DATE = "creation_date";

    private SQLiteDatabase db;
    private final Context context;
    private RutaDBOpenHelper dbHelper;

    public RutaDBAdapter(Context _context) {
        this.context = _context;
        dbHelper = new RutaDBOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void close() { db.close();}
    public void open() throws SQLiteException {
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            db = dbHelper.getReadableDatabase();
        }
    }

    // begin ************************
    // Insert a new route
    public long insertRoute(RutaItem _route) {
        // Create a new row of values to insert.
        ContentValues newRouteValues = new ContentValues();
        // Assign values for each row.
        newRouteValues.put(KEY_NUMERO, _route.getRoute());
        newRouteValues.put(KEY_EMPRESA, _route.getCompany());
        newRouteValues.put(KEY_TIEMPO, _route.getTme());
        newRouteValues.put(KEY_LUGARES, _route.getPlaces());
        newRouteValues.put(KEY_CREATION_DATE, _route.getCreated().getTime());
        // Insert the row.
        return db.insert(DATABASE_TABLE, null, newRouteValues);
    }
    // Remove a route based on its index
    public boolean removeRoute(long _rowIndex) {
        return db.delete(DATABASE_TABLE, KEY_ID + "=" + _rowIndex, null) > 0;
    }
    // Update a Route
    public boolean updateRoute(long _rowIndex, String _route) {
        ContentValues newValue = new ContentValues();
        newValue.put(KEY_NUMERO, _route);
        return db.update(DATABASE_TABLE, newValue, KEY_ID + "=" + _rowIndex, null) > 0;
    }

    //Read a Route All
    public Cursor getAllRutaItemsCursor() {
        return db.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_NUMERO,
                KEY_CREATION_DATE }, null, null, null, null, null);
    }
    public Cursor setCursorRutaItem(long _rowIndex) throws SQLException {
        Cursor result = db.query(true, DATABASE_TABLE, new String[] { KEY_ID,
                        KEY_NUMERO }, KEY_ID + "=" + _rowIndex, null, null, null, null,
                null);
        if ((result.getCount() == 0) || !result.moveToFirst()) {
            throw new SQLException("No to do routes found for row: " + _rowIndex);
        }
        return result;
    }

    //Read a Task single
    public RutaItem getRutaItem(long _rowIndex) throws SQLException {
        Cursor cursor = db.query(true, DATABASE_TABLE, new String[] { KEY_ID,
                        KEY_NUMERO, KEY_EMPRESA, KEY_LUGARES, KEY_TIEMPO }, KEY_ID + "=" + _rowIndex, null, null, null, null,
                null);
        if ((cursor.getCount() == 0) || !cursor.moveToFirst()) {
            throw new SQLException("No route found for row: " + _rowIndex);
        }
        String route = cursor.getString(cursor.getColumnIndex(KEY_NUMERO));
        String company = cursor.getString(cursor.getColumnIndex(KEY_EMPRESA));
        String places = cursor.getString(cursor.getColumnIndex(KEY_LUGARES));
        String tme = cursor.getString(cursor.getColumnIndex(KEY_TIEMPO));
        long created = cursor.getLong(cursor.getColumnIndex(KEY_CREATION_DATE));
        RutaItem result = new RutaItem(route, company, places, tme, new Date(created));
        return result;
    }
    //end

    // ***************************

    private static class RutaDBOpenHelper extends SQLiteOpenHelper {
        public RutaDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }
        // SQL Statement to create a new database.
        private static final String DATABASE_CREATE = "create table "
                + DATABASE_TABLE + " ("
                + KEY_ID + " integer primary key autoincrement, "
                + KEY_EMPRESA + " text not null, "
                + KEY_LUGARES + "text not null"
                + KEY_NUMERO + "text not null"
                + KEY_TIEMPO + "text not null"
                + KEY_CREATION_DATE + " long);";
        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(DATABASE_CREATE);
        }
        @Override
        public void onUpgrade(SQLiteDatabase _db, int _oldVersion,
                              int _newVersion) {
            Log.w("RouteDBAdapter", "Upgrading from version " + _oldVersion
                    + " to " + _newVersion
                    + ", which will destroy all old data");
            // Drop the old table.
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            // Create a new one.
            onCreate(_db);
        }
    }
}
