package ng.com.tinweb.www.languagetranslator.data.language;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

import ng.com.tinweb.www.languagetranslator.data.DbContract;

/**
 * Created by kamiye on 13/09/2016.
 */
public class LanguageDbHelper extends SQLiteOpenHelper implements LanguageDataStore {

    // Note: if you change the database schema, you must increment the database version

    public LanguageDbHelper(Context context) {
        super(context, DbContract.DATABASE_NAME, null, DbContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DbContract.SQL_CREATE_LANGUAGE_ENTRIES);
        sqLiteDatabase.execSQL(DbContract.SQL_CREATE_TRANSLATIONS_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(DbContract.SQL_DELETE_LANGUAGE_ENTRIES);
        sqLiteDatabase.execSQL(DbContract.SQL_DELETE_TRANSLATIONS_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    @Override
    public void saveLanguages(JSONObject languages, DbActionCallback callback) {
        SQLiteDatabase database = getWritableDatabase();

        Iterator<String> keys = languages.keys();

        while (keys.hasNext()) {
            ContentValues values = new ContentValues();
            String key = keys.next();
            try {
                String value = languages.getString(key);
                values.put(DbContract.LanguagesSchema.COLUMN_LANGUAGE_KEY, key);
                values.put(DbContract.LanguagesSchema.COLUMN_LANGUAGE_VALUE, value);
                database.insert(DbContract.LanguagesSchema.TABLE_NAME, null, values);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        callback.onFinish();
    }

    @Override
    public boolean checkLanguages() {
        SQLiteDatabase database = getReadableDatabase();

        String[] projection = {
                DbContract.LanguagesSchema._ID,
                DbContract.LanguagesSchema.COLUMN_LANGUAGE_KEY
        };
        Cursor cursor = database.query(
                DbContract.LanguagesSchema.TABLE_NAME, projection,
                null,
                null,
                null,
                null,
                null
        );
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        return false;
    }

    @Override
    public HashMap<String, String> getAllLanguages() {
        SQLiteDatabase database = getReadableDatabase();
        String[] projection = {
                DbContract.LanguagesSchema.COLUMN_LANGUAGE_KEY,
                DbContract.LanguagesSchema.COLUMN_LANGUAGE_VALUE
        };
        Cursor cursor = database.query(
                DbContract.LanguagesSchema.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        if (cursor.moveToFirst()) {
            HashMap<String, String> languageMap = new HashMap<>();

            while(cursor.moveToNext()) {
                String key = cursor.getString(
                        cursor.getColumnIndexOrThrow(DbContract.LanguagesSchema.COLUMN_LANGUAGE_KEY)
                );
                String value = cursor.getString(
                        cursor.getColumnIndexOrThrow(DbContract.LanguagesSchema.COLUMN_LANGUAGE_VALUE)
                );
                languageMap.put(key, value);
            }
            cursor.close();
            return languageMap;
        }
        cursor.close();
        return null;
    }

    @Override
    public void deleteLanguages() {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("delete from " + DbContract.LanguagesSchema.TABLE_NAME);
    }
}
