package io.github.cluo29.contextdatareading.providers;



import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.Environment;
import android.provider.BaseColumns;
import android.util.Log;

import io.github.cluo29.contextdatareading.providers.DatabaseHelper;

import java.io.File;
import java.util.HashMap;

/**
 * Created by Comet on 03/10/16.
 */
public class Audio_Provider extends ContentProvider {

    public static final int DATABASE_VERSION = 1;

    public static String AUTHORITY = "io.github.cluo29.contextdatareading.provider.audio";

    // ContentProvider query paths
    private static final int SENSOR_DEV = 1;
    private static final int SENSOR_DEV_ID = 2;

    public static final class Audio_Result implements BaseColumns {
        private Audio_Result() {
        }

        public static final Uri CONTENT_URI = Uri.parse("content://"
                + Audio_Provider.AUTHORITY + "/audio");
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.contextdatareading.audio";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.contextdatareading.audio";

        public static final String _ID = "_id";
        public static final String TIMESTAMP = "timestamp";
        public static final String DEVICE_ID = "device_id";
        public static final String FILENAME = "FILENAME";
    }

    public static String DATABASE_NAME = "audio.db";
    public static final String[] DATABASE_TABLES = { "audio" };
    public static final String[] TABLES_FIELDS = {
            Audio_Result._ID + " integer primary key autoincrement,"
                    + Audio_Result.TIMESTAMP + " real default 0,"
                    + Audio_Result.DEVICE_ID + " text default '',"
                    + Audio_Result.FILENAME + " text default ''"
    };

    private static UriMatcher sUriMatcher = null;
    private static HashMap<String, String> sensorMap = null;
    private static DatabaseHelper databaseHelper = null;
    private static SQLiteDatabase database = null;

    private boolean initializeDB() {
        if (databaseHelper == null) {
            databaseHelper = new DatabaseHelper( getContext(), DATABASE_NAME, null, DATABASE_VERSION, DATABASE_TABLES, TABLES_FIELDS );
        }
        if( databaseHelper != null && ( database == null || ! database.isOpen() )) {
            database = databaseHelper.getWritableDatabase();
        }
        return( database != null && databaseHelper != null);
    }

    public static void resetDB( Context c ) {
        Log.d("AWARE", "Resetting " + DATABASE_NAME + "...");

        File db = new File(DATABASE_NAME);
        db.delete();
        databaseHelper = new DatabaseHelper( c, DATABASE_NAME, null, DATABASE_VERSION, DATABASE_TABLES, TABLES_FIELDS);
        if( databaseHelper != null ) {
            database = databaseHelper.getWritableDatabase();
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if( ! initializeDB() ) {
            Log.w(AUTHORITY,"Database unavailable...");
            return 0;
        }

        int count = 0;
        switch (sUriMatcher.match(uri)) {
            case SENSOR_DEV:
                database.beginTransaction();
                count = database.delete(DATABASE_TABLES[0], selection,
                        selectionArgs);
                database.setTransactionSuccessful();
                database.endTransaction();
                break;
            default:

                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case SENSOR_DEV:
                return Audio_Result.CONTENT_TYPE;
            case SENSOR_DEV_ID:
                return Audio_Result.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
        if( ! initializeDB() ) {
            Log.w(AUTHORITY,"Database unavailable...");
            return null;
        }

        ContentValues values = (initialValues != null) ? new ContentValues(
                initialValues) : new ContentValues();

        switch (sUriMatcher.match(uri)) {
            case SENSOR_DEV:
                database.beginTransaction();
                long accel_id = database.insertWithOnConflict(DATABASE_TABLES[0],
                        Audio_Result.DEVICE_ID, values, SQLiteDatabase.CONFLICT_IGNORE);
                database.setTransactionSuccessful();
                database.endTransaction();
                if (accel_id > 0) {
                    Uri accelUri = ContentUris.withAppendedId(
                            Audio_Result.CONTENT_URI, accel_id);
                    getContext().getContentResolver().notifyChange(accelUri, null);
                    return accelUri;
                }
                throw new SQLException("Failed to insert row into " + uri);
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        if( ! initializeDB() ) {
            Log.w(AUTHORITY,"Database unavailable...");
            return 0;
        }

        int count = 0;
        switch ( sUriMatcher.match(uri) ) {
            case SENSOR_DEV:
                database.beginTransaction();
                for (ContentValues v : values) {
                    long id;
                    try {
                        id = database.insertOrThrow( DATABASE_TABLES[0], Audio_Result.DEVICE_ID, v );
                    } catch ( SQLException e ) {
                        id = database.replace( DATABASE_TABLES[0], Audio_Result.DEVICE_ID, v );
                    }
                    if( id <= 0 ) {
                        Log.w("Light.TAG", "Failed to insert/replace row into " + uri);
                    } else {
                        count++;
                    }
                }
                database.setTransactionSuccessful();
                database.endTransaction();
                getContext().getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public boolean onCreate() {

        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(Audio_Provider.AUTHORITY, DATABASE_TABLES[0],
                SENSOR_DEV);
        sUriMatcher.addURI(Audio_Provider.AUTHORITY, DATABASE_TABLES[0] + "/#",
                SENSOR_DEV_ID);


        sensorMap = new HashMap<String, String>();
        sensorMap.put(Audio_Result._ID, Audio_Result._ID);
        sensorMap.put(Audio_Result.TIMESTAMP, Audio_Result.TIMESTAMP);
        sensorMap.put(Audio_Result.DEVICE_ID, Audio_Result.DEVICE_ID);
        sensorMap.put(Audio_Result.FILENAME, Audio_Result.FILENAME);

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        if( ! initializeDB() ) {
            Log.w(AUTHORITY,"Database unavailable...");
            return null;
        }

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        switch (sUriMatcher.match(uri)) {
            case SENSOR_DEV:
                qb.setTables(DATABASE_TABLES[0]);
                qb.setProjectionMap(sensorMap);
                break;
            default:

                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        try {
            Cursor c = qb.query(database, projection, selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(getContext().getContentResolver(), uri);
            return c;
        } catch (IllegalStateException e) {

            Log.e("Aware.TAG", e.getMessage());

            return null;
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        if( ! initializeDB() ) {
            Log.w(AUTHORITY,"Database unavailable...");
            return 0;
        }

        int count = 0;
        switch (sUriMatcher.match(uri)) {
            case SENSOR_DEV:
                database.beginTransaction();
                count = database.update(DATABASE_TABLES[0], values, selection,
                        selectionArgs);
                database.setTransactionSuccessful();
                database.endTransaction();
                break;

            default:

                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}