package ng.com.tinweb.www.languagetranslator.data.language;

import android.content.Context;

import org.json.JSONObject;

import java.util.HashMap;

import ng.com.tinweb.www.languagetranslator.LanguageTranslatorApplication;

/**
 * Created by kamiye on 13/09/2016.
 */
public class Language {

    private LanguageDataStore dataStore;

    public Language() {
        initialiseDataStore();
    }

    public boolean isLanguagesSet() {
        return dataStore.checkLanguages();
    }

    private void initialiseDataStore() {
        Context context = LanguageTranslatorApplication.getContext();
        dataStore = new LanguageDbHelper(context);
    }

    public void setLanguages(JSONObject languages, LanguageDataStore.DbActionCallback callback) {
        dataStore.saveLanguages(languages, callback);
    }

    public HashMap<String, String> getLanguages() {
        return dataStore.getAllLanguages();
    }

    public void removeAll() {
        dataStore.deleteLanguages();
    }
}
