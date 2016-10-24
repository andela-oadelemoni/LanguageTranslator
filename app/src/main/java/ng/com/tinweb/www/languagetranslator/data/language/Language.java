package ng.com.tinweb.www.languagetranslator.data.language;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import ng.com.tinweb.www.languagetranslator.data.TranslatorAPI;
import retrofit2.Call;
import retrofit2.Callback;

import static ng.com.tinweb.www.languagetranslator.data.TranslatorAPI.TranslationService.retrofit;

/**
 * Created by kamiye on 13/09/2016.
 */
public class Language implements Callback<JsonObject> {

    private static LanguageDataStore dataStore;
    private ApiCallback apiCallback;

    public boolean isLanguagesSet() {
        initialiseDataStore();
        return dataStore.checkLanguages();
    }

    public void setLanguages(JSONObject languages, LanguageDataStore.DbActionCallback callback) {
        initialiseDataStore();
        dataStore.saveLanguages(languages, callback);
    }

    public void get(ApiCallback callback) {
        if (!isLanguagesSet()) {
            getLanguagesFromApi(callback);
        }
        else {
            callback.onSuccess();
        }
    }

    private void getLanguagesFromApi(final ApiCallback callback) {
        if (apiCallback == null) {
            apiCallback = callback;
        }
        TranslatorAPI.TranslationService translationService =
                retrofit.create(TranslatorAPI.TranslationService.class);
        Call<JsonObject> jsonObjectCall =
                translationService.getLanguages(TranslatorAPI.API_KEY);

        jsonObjectCall.enqueue(this);
    }

    public void removeAll() {
        initialiseDataStore();
        dataStore.deleteLanguages();
    }

    public static HashMap<String, String> getLanguagesFromLocalStorage() {
        initialiseDataStore();
        return dataStore.getAllLanguages();
    }

    @Override
    public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
        JsonObject languages = response.body().getAsJsonObject("langs");
        try {
            JSONObject lang = new JSONObject(languages.toString());
            setLanguages(lang, new LanguageDataStore.DbActionCallback() {
                @Override
                public void onFinish() {
                    apiCallback.onSuccess();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(Call<JsonObject> call, Throwable t) {
        apiCallback.onError(t.getLocalizedMessage());
    }

    private static void initialiseDataStore() {
        dataStore = LanguageDbHelper.getInstance();
    }

    public interface ApiCallback {
        void onSuccess();
        void onError(String message);
    }
}
