package ng.com.tinweb.www.languagetranslator.data.language;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by kamiye on 13/09/2016.
 */
public class Language {

    private static LanguageDataStore dataStore;

    public boolean isLanguagesSet() {
        return dataStore.checkLanguages();
    }

    private static void initialiseDataStore() {
        dataStore = LanguageDbHelper.getInstance();
    }

    public void setLanguages(JSONObject languages, LanguageDataStore.DbActionCallback callback) {
        initialiseDataStore();
        dataStore.saveLanguages(languages, callback);
    }

    public static HashMap<String, String> getLanguages() {
        initialiseDataStore();
        return dataStore.getAllLanguages();
    }

    public void removeAll() {
        initialiseDataStore();
        dataStore.deleteLanguages();
    }
}
