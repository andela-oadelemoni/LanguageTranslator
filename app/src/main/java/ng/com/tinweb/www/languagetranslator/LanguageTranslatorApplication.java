package ng.com.tinweb.www.languagetranslator;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import ng.com.tinweb.www.languagetranslator.data.translation.TranslationsDbHelper;

/**
 * Created by kamiye on 08/09/2016.
 */
public class LanguageTranslatorApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;

        getTableNames();
        // TODO clean up old translations
    }

    public static Context getContext() {
        return context;
    }

    private void getTableNames() {
        TranslationsDbHelper dbHelper = new TranslationsDbHelper(context);
        Cursor c = dbHelper.getReadableDatabase().rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
                Log.i("TABLE_NAME", c.getString(0));
                c.moveToNext();
            }
        }
    }

}
