package ng.com.tinweb.www.languagetranslator;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import ng.com.tinweb.www.languagetranslator.data.Language;
import ng.com.tinweb.www.languagetranslator.data.LanguageDataStore;
import ng.com.tinweb.www.languagetranslator.data.TranslatorAPI;

/**
 * Created by kamiye on 08/09/2016.
 */
public class LanguageTranslatorApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;
    }

    public static Context getContext() {
        return context;
    }

}
