package com.drprog.happystory.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.drprog.happystory.db.table.SQLBaseTable;
import com.drprog.happystory.db.table.UserTrackTable;

import java.util.Collections;
import java.util.List;

public final class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "journal.db";
    private static final int DATABASE_VERSION = 1;
    public static final String LOG_TAG = "DatabaseHelper";

    private volatile static DatabaseHelper sInstance = null;
    private static final Object sLock = new Object();

    private final UserTrackTable mUserTrackTable = new UserTrackTable();

    public static DatabaseHelper getInstance(Context _context) {
        if (_context == null)
            throw new RuntimeException("DatabaseHelper not initialized. You cannot get the instance until you initialized that");

        // This ensures that in cases where helper is already initialized (i.e., most of the time),
        // the volatile field is only accessed once,
        // which can improve the method's overall performance by as much as 25 percent.
        DatabaseHelper instance = sInstance;
        if (instance == null) {
            synchronized (sLock) {
                instance = sInstance;
                if (instance == null) {
                    sInstance = instance = new DatabaseHelper(_context.getApplicationContext());
                }
            }
        }
           return instance;
    }

    private DatabaseHelper(Context _context) {
        super(_context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase _db) {
        try {
            Log.i(LOG_TAG, "onCreate");
            UserTrackTable.onCreateDb(_db);
        } catch (SQLException e) {
            Log.e(LOG_TAG, "Can't create database. Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void dropTables(final SQLiteDatabase _db) {
        Log.i(LOG_TAG, "Drop database");
        try {
            _db.rawQuery("DROP TABLE " + UserTrackTable.TABLE_NAME, null);
        } catch (SQLException e) {
            Log.e(LOG_TAG, "Can't drop database. Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(final SQLiteDatabase _db, final int _oldVersion, final int _newVersion) {
        Log.i(LOG_TAG, "onUpdate from v." + _oldVersion + " to v." + _newVersion);
//            switch (_oldVersion) {
//                case 1:
//                    upgradeToSecondVersion(_db, _oldVersion, _newVersion);
//                case 2:
//                    upgradeToThirdVersion(_db, _oldVersion, _newVersion);
//            }
        Log.i(LOG_TAG, "Database has been upgraded successfully.");
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        mUserTrackTable.onOpenDb(db);
    }

    public UserTrackTable openUserTrackTable(boolean _withWritableAccess){
        openDb(_withWritableAccess);
        return mUserTrackTable;
    }

    public <T> List<T> convertCursorToList(Cursor _cursor, Class<? extends SQLBaseTable> _table){
        if (UserTrackTable.class.equals(_table)){
            return (List<T>) mUserTrackTable.getList(_cursor);
        }
        return Collections.emptyList();
    }

    private void openDb(boolean _withWritableAccess) {
        if (_withWritableAccess){
            getWritableDatabase();
        }else {
            getReadableDatabase();
        }
    }
}
