package com.drprog.happystory.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import com.drprog.happystory.db.table.SQLBaseTable;
import com.drprog.happystory.db.table.UserTrackTable;

public class TrackProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private DatabaseHelper mOpenHelper;

    private static final int USER_TRACK = 100;
    private static final int USER_TRACK_ID = 101;
//    private static final int USER_TRACK_WITH_LIMIT = 102;
    private static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = SQLBaseTable.CONTENT_AUTHORITY;

        matcher.addURI(authority, UserTrackTable.TABLE_NAME, USER_TRACK);
//        matcher.addURI(authority, UserTrackTable.TABLE_NAME + "/limit/#", USER_TRACK_WITH_LIMIT);
        matcher.addURI(authority, UserTrackTable.TABLE_NAME + "/#", USER_TRACK_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = DatabaseHelper.getInstance(getContext());
        return true;
    }

    private Cursor queryUserTrackTable(Uri _uri, String[] _projection, String _selection,
                                       String[] _selectionArgs, String _sortOrder){
        String limitClouse = null;
        final String limit = _uri.getQueryParameter(UserTrackTable.URI_PARAM_QUERY_LIMIT);
        if (TextUtils.isEmpty(limit)) {
            final String offset = _uri.getQueryParameter(UserTrackTable.URI_PARAM_QUERY_OFFSET);
            limitClouse = (TextUtils.isEmpty(offset) ? "" : offset + ",") + limit;
        }
        return mOpenHelper.openUserTrackTable(false).getAll(
                _projection,
                _selection,
                _selectionArgs,
                _sortOrder,
                limitClouse);
    }

    @Override
    public Cursor query(Uri _uri, String[] _projection, String _selection, String[] _selectionArgs, String _sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(_uri)) {
            // "user_track"
            case USER_TRACK: {
                retCursor = queryUserTrackTable(_uri, _projection, _selection, _selectionArgs, _sortOrder);
                break;
            }
//            // "user_track/limit/#"
//            case USER_TRACK_WITH_LIMIT: {
//                limit = _uri.getPathSegments().get(2);
//                retCursor = mOpenHelper.openUserTrackTable(false).getAll(
//                        _projection,
//                        _selection,
//                        _selectionArgs,
//                        _sortOrder,
//                        limit
//                );
//                break;
//            }
            // "user_track/#"
            case USER_TRACK_ID: {
                retCursor = mOpenHelper.openUserTrackTable(false).get(
                        _projection,
                        ContentUris.parseId(_uri),
                        _sortOrder
                );
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + _uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), _uri);
        return retCursor;
    }

    @Override
    public String getType(Uri _uri) {
        final int match = sUriMatcher.match(_uri);

        switch (match) {
            case USER_TRACK:
                return UserTrackTable.CONTENT_TYPE;
            case USER_TRACK_ID:
                return UserTrackTable.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + _uri);
        }
    }

    @Override
    public Uri insert(Uri _uri, ContentValues _values) {
        final int match = sUriMatcher.match(_uri);
        Uri returnUri;

        switch (match) {
            case USER_TRACK: {

                long _id = mOpenHelper.openUserTrackTable(true).updateOrInsert(_values);
                if ( _id > 0 )
                    returnUri = UserTrackTable.buildUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + _uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown _uri: " + _uri);
        }
        getContext().getContentResolver().notifyChange(_uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri _uri, String _selection, String[] _selectionArgs) {
        final int match = sUriMatcher.match(_uri);
        int rowsDeleted;
        switch (match) {
            case USER_TRACK:
                rowsDeleted = mOpenHelper.openUserTrackTable(true).delete(_selection, _selectionArgs);
                break;
            case USER_TRACK_ID:
                rowsDeleted = mOpenHelper.openUserTrackTable(true).delete(ContentUris.parseId(_uri));
                break;
            default:
                throw new UnsupportedOperationException("Unknown _uri: " + _uri);
        }
        // Because a null deletes all rows
        if (_selection == null || rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(_uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri _uri, ContentValues _values, String _selection, String[] _selectionArgs) {
        final int match = sUriMatcher.match(_uri);
        int rowsUpdated = 0;

        switch (match) {
            case USER_TRACK:
                rowsUpdated = mOpenHelper.openUserTrackTable(true)
                        .update(_values, _selection, _selectionArgs);
                break;
            case USER_TRACK_ID:
                rowsUpdated = mOpenHelper.openUserTrackTable(true)
                        .update(ContentUris.parseId(_uri), _values);
                break;
            default:
                throw new UnsupportedOperationException("Unknown _uri: " + _uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(_uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri _uri, ContentValues[] _values) {
        final int match = sUriMatcher.match(_uri);
        switch (match) {
            case USER_TRACK:
                int returnCount = mOpenHelper.openUserTrackTable(true)
                        .bulkInsertOrUpdate(_values);

                getContext().getContentResolver().notifyChange(_uri, null);
                return returnCount;
            default:
                return super.bulkInsert(_uri, _values);
        }
    }
}