package ng.com.tinweb.www.languagetranslator.data.translation;

import android.content.Context;

import ng.com.tinweb.www.languagetranslator.LanguageTranslatorApplication;

/**
 * Created by kamiye on 13/09/2016.
 */
public class Translation {

    private TranslationDataStore dataStore;

    public Translation() {
        initialiseDataStore();
    }

    public boolean isExisting(String lang, String input) {
        return dataStore.isSaved(lang, input);
    }

    public void save(String lang, String input, String output) {
        dataStore.saveTranslation(lang, input, output);
    }

    public String get(String lang, String input) {
        return dataStore.getTranslation(lang, input);
    }

    private void initialiseDataStore() {
        Context context = LanguageTranslatorApplication.getContext();
        dataStore = new TranslationsDbHelper(context);
    }

}
