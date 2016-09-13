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
        getLanguages();
    }

    public static Context getContext() {
        return context;
    }

    private void getLanguages() {
        final Language languageModel = new Language();
        // check if languages have been set
        if (!languageModel.isLanguagesSet()) {
            // get the languages
            TranslatorAPI.getLanguages(new TranslatorAPI.GetLanguageCallback() {
                @Override
                public void onSuccess(JSONObject languages) {
                    setLanguages(languageModel, languages);
                }

                @Override
                public void onError() {
                    Log.i("ERROR", "");
                }
            });
        }
    }

    private void setLanguages(Language languageModel, JSONObject languages) {
        languageModel.setLanguages(languages, new LanguageDataStore.DbActionCallback() {
            @Override
            public void onFinish() {
                // TODO proceed to the application
            }
        });
    }
}
