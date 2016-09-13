package ng.com.tinweb.www.languagetranslator.data;

/**
 * Created by kamiye on 13/09/2016.
 */
public interface TranslationDataStore {

    void saveTranslation(String lang, String input, String output);

    boolean isSaved(String lang, String input);

    String getTranslation(String lang, String input);

}
