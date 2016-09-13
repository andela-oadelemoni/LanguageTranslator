package ng.com.tinweb.www.languagetranslator.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by kamiye on 13/09/2016.
 */
public class TranslationsDbHelper extends SQLiteOpenHelper implements TranslationDataStore {

    // Note: if you change the database schema, you must increment the database version

    public TranslationsDbHelper(Context context) {
        super(context, DbContract.DATABASE_NAME, null, DbContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DbContract.SQL_CREATE_TRANSLATIONS_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVerson, int newVersion) {
        sqLiteDatabase.execSQL(DbContract.SQL_DELETE_TRANSLATIONS_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    @Override
    public void saveTranslation(String lang, String input, String output) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbContract.SavedTranslationsSchema.COLUMN_LANGUAGE, lang);
        values.put(DbContract.SavedTranslationsSchema.COLUMN_INPUT, input);
        values.put(DbContract.SavedTranslationsSchema.COLUMN_OUTPUT, output);
        database.insert(DbContract.SavedTranslationsSchema.TABLE_NAME, null, values);

    }

    @Override
    public boolean isSaved(String lang, String input) {
        SQLiteDatabase database = getReadableDatabase();

        String[] projection = {
                DbContract.SavedTranslationsSchema._ID,
                DbContract.SavedTranslationsSchema.COLUMN_OUTPUT
        };
        String selection = DbContract.SavedTranslationsSchema.COLUMN_LANGUAGE + " = ? " +
                "AND " + DbContract.SavedTranslationsSchema.COLUMN_INPUT + " = ?";
        String[] selectionArgs = {lang, input};
        Cursor cursor = database.query(
                DbContract.SavedTranslationsSchema.TABLE_NAME, projection,
                selection,
                selectionArgs,
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
    public String getTranslation(String lang, String input) {
        SQLiteDatabase database = getReadableDatabase();

        String[] projection = {
                DbContract.SavedTranslationsSchema._ID,
                DbContract.SavedTranslationsSchema.COLUMN_OUTPUT
        };
        String selection = DbContract.SavedTranslationsSchema.COLUMN_LANGUAGE + " = ? " +
                "AND " + DbContract.SavedTranslationsSchema.COLUMN_INPUT + " = ?";
        String[] selectionArgs = {lang, input};
        Cursor cursor = database.query(
                DbContract.LanguagesSchema.TABLE_NAME, projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        if (cursor.getCount() > 0) {
            String translation = cursor.getString(
                    cursor.getColumnIndexOrThrow(DbContract.SavedTranslationsSchema.COLUMN_OUTPUT)
            );
            cursor.close();
            return translation;
        }
        return null;
    }

}
