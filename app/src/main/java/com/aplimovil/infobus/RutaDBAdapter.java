package com.aplimovil.infobus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Date;


public class RutaDBAdapter {
    private static final String DATABASE_NAME = "todoList.db";
    private static final String DATABASE_TABLE = "todoItems";
    private static final int DATABASE_VERSION = 1;
    public static final String KEY_ID = "_id";
    public static final String KEY_RUTE = "rute";
    public static final String KEY_CREATION_DATE = "creation_date";

    private SQLiteDatabase db;
    private final Context context;
    private ToDoDBOpenHelper dbHelper;

    public RutaDBAdapter(Context _context) {
        this.context = _context;
        dbHelper = new ToDoDBOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void close() {
        db.close();
    }
    public void open() throws SQLiteException {
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            db = dbHelper.getReadableDatabase();
        }
    }

    // Insert a new task
    public long insertRute(RutItem _rute) {
// Create a new row of values to insert.
        ContentValues newRuteValues = new ContentValues();
// Assign values for each row.
        newRuteValues.put(KEY_RUTE, _rute.getRute());
        newRuteValues.put(KEY_CREATION_DATE, _rute.getCreated().getTime());
// Insert the row.
        return db.insert(DATABASE_TABLE, null, newRuteValues);
    }
    // Remove a task based on its index
    public boolean removeTask(long _rowIndex) {
        return db.delete(DATABASE_TABLE, KEY_ID + "=" + _rowIndex, null) > 0;
    }
    // Update a task
    public boolean updateTask(long _rowIndex, String _rute) {
        ContentValues newValue = new ContentValues();
        newValue.put(KEY_RUTE, _rute);
        return db.update(DATABASE_TABLE, newValue, KEY_ID + "=" + _rowIndex, null) > 0;
    }

    //Read a Task All
    public Cursor getAllToDoItemsCursor() {
        return db.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_RUTE,
                KEY_CREATION_DATE }, null, null, null, null, null);
    }
    public Cursor setCursorToToDoItem(long _rowIndex) throws SQLException {
        Cursor result = db.query(true, DATABASE_TABLE, new String[] { KEY_ID,
                        KEY_RUTE}, KEY_ID + "=" + _rowIndex, null, null, null, null,
                null);
        if ((result.getCount() == 0) || !result.moveToFirst()) {
            throw new SQLException("No to do items found for row: " + _rowIndex);
        }
        return result;
    }

    //Read a Task single
    public RutItem getToDoItem(long _rowIndex) throws SQLException {
        Cursor cursor = db.query(true, DATABASE_TABLE, new String[] { KEY_ID,
                        KEY_RUTE }, KEY_ID + "=" + _rowIndex, null, null, null, null,
                null);
        if ((cursor.getCount() == 0) || !cursor.moveToFirst()) {
            throw new SQLException("No to do item found for row: " + _rowIndex);
        }
        String task = cursor.getString(cursor.getColumnIndex(KEY_RUTE));
        long created =
                cursor.getLong(cursor.getColumnIndex(KEY_CREATION_DATE));
        RutItem result = new RutItem(task, new Date(created));
        return result;
    }
    //end

    private static class ToDoDBOpenHelper extends SQLiteOpenHelper {
        public ToDoDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }
        // SQL Statement to create a new database.
        private static final String DATABASE_CREATE = "create table "
                + DATABASE_TABLE + " (" + KEY_ID
                + " integer primary key autoincrement, " + KEY_RUTE
                + " text not null, " + KEY_CREATION_DATE + " long);";
        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(DATABASE_CREATE);
        }
        @Override
        public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
            Log.w("TaskDBAdapter", "Upgrading from version "
                    + _oldVersion
                    + " to " + _newVersion
                    + ", which will destroy all old data");
            // Drop the old table.
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            // Create a new one.
            onCreate(_db);
        }
    }

}
