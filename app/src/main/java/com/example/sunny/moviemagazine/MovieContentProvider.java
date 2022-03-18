package com.example.sunny.moviemagazine;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by sunny on 27-05-2018.
 */

public class MovieContentProvider extends ContentProvider {

    public static final int TABLEID = 55;
    public static final int ROWID = 102;
    private static final UriMatcher favUriMatcher = buildUriMatcher();
    private DataBase dataBase;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(MovieAttributes.AUTHORITY, MovieAttributes.PATH_TASKS, TABLEID);
        uriMatcher.addURI(MovieAttributes.AUTHORITY, MovieAttributes.PATH_TASKS + "/*", ROWID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        dataBase = new DataBase(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = dataBase.getReadableDatabase();
        int match = favUriMatcher.match(uri);
        Cursor cursor = null;
        switch (match) {
            case TABLEID:
                cursor = db.query(MovieAttributes.dataAttributes.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case ROWID:
                cursor = db.query(MovieAttributes.dataAttributes.TABLE_NAME, projection, MovieAttributes.dataAttributes.COLUMN_MOVIEID + "=" + selection, null, null, null, null);
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri" + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Unknown Uri");
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        final SQLiteDatabase db = dataBase.getWritableDatabase();
        long id;
        int match = favUriMatcher.match(uri);
        Uri uriMatched = null;
        switch (match) {
            case TABLEID:
                id = db.insert(MovieAttributes.dataAttributes.TABLE_NAME, null, values);
                if (id > 0) {
                    uriMatched = ContentUris.withAppendedId(MovieAttributes.dataAttributes.CONTENT_URI, id);
                }
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return uriMatched;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase sqDatabase = dataBase.getWritableDatabase();
        int match = favUriMatcher.match(uri);
        int favMoviedeleted;
        if (selection == null) {
            selection = "1";
        }
        switch (match) {
            case TABLEID:
                favMoviedeleted = sqDatabase.delete(MovieAttributes.dataAttributes.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri:" + uri);
        }
        if (favMoviedeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return favMoviedeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        throw new UnsupportedOperationException("No Updation done");
    }
}
