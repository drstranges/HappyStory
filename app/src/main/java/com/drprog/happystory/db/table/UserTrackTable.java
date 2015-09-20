package com.drprog.happystory.db.table;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.drprog.happystory.db.model.TrackPoint;

public class UserTrackTable extends SQLBaseTable<TrackPoint> {

    public static final String TABLE_NAME = "user_track";

    public static final String CONTENT_TYPE = CONTENT_TYPE_PREFIX + TABLE_NAME;
    public static final String CONTENT_ITEM_TYPE = CONTENT_ITEM_TYPE_PREFIX + TABLE_NAME;

    public static final String URI_PARAM_QUERY_OFFSET = "offset";
    public static final String URI_PARAM_QUERY_LIMIT = "limit";

    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

    public static final String FIELD_VALUE = "value";
    public static final String FIELD_TIMESTAMP = "timestamp";
    protected static final String SCRIPT_CREATE_TABLE = "create table " + TABLE_NAME + " ( " +
            FIELD_ID + " integer primary key, " +
            FIELD_TIMESTAMP + " integer(4) not null default (strftime('%s','now'), " +
            FIELD_VALUE + " integer not null default 0" +
            ");";

    public static void onCreateDb(SQLiteDatabase db) throws SQLException {
        db.execSQL(SCRIPT_CREATE_TABLE);
    }

    public static Uri buildUri(long _id) {
        return ContentUris.withAppendedId(CONTENT_URI, _id);
    }

    public static Uri buildUriForAllWithLimit(long _offset, long _limit) {
        return CONTENT_URI.buildUpon()
                .appendQueryParameter(URI_PARAM_QUERY_OFFSET, String.valueOf(_offset))
                .appendQueryParameter(URI_PARAM_QUERY_LIMIT, String.valueOf(_limit))
                .build();
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected TrackPoint loadDbItem(final Cursor _cur) {
        final TrackPoint item = new TrackPoint();
        item.id = _cur.getLong(_cur.getColumnIndex(FIELD_ID));
        item.timestamp = _cur.getLong(_cur.getColumnIndex(FIELD_TIMESTAMP));
        item.value = _cur.getInt(_cur.getColumnIndex(FIELD_VALUE));
        return item;
    }

    @Override
    protected ContentValues convertToCV(final TrackPoint item) {
        ContentValues cv = new ContentValues();
        if (item.id != null) {
            cv.put(FIELD_ID, item.id);
        }
        if (item.timestamp != null) {
            cv.put(FIELD_TIMESTAMP, item.timestamp);
        }
        cv.put(FIELD_VALUE, item.value);
        return cv;
    }

}