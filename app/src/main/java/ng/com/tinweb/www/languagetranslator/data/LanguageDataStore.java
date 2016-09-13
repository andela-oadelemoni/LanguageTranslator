package ng.com.tinweb.www.languagetranslator.data;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by kamiye on 08/09/2016.
 */
public interface LanguageDataStore {

    void saveLanguages(JSONObject languages, DbActionCallback callback);

    boolean checkLanguages();

    HashMap<String, String> getAllLanguages();

    void deleteLanguages();

    interface DbActionCallback {
        void onFinish();
    }
}
