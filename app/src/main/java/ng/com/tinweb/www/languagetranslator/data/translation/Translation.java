package ng.com.tinweb.www.languagetranslator.data.translation;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import ng.com.tinweb.www.languagetranslator.LanguageTranslatorApplication;
import ng.com.tinweb.www.languagetranslator.data.TranslatorAPI;
import ng.com.tinweb.www.languagetranslator.data.language.Language;

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

    public void save(String lang, String text, String translation) {
        dataStore.saveTranslation(lang, text, translation);
    }

    public void get(String lang, String text, ApiCallback callback) {
        if (isExisting(lang, text)) {
            String translation = getFromLocalStorage(lang, text);
            callback.onSuccess(translation);
        }
        else {
            getFromAPI(lang, text, callback);
        }
    }

    public String getFromLocalStorage(String lang, String text) {
        return dataStore.getTranslation(lang, text);
    }

    public void getFromAPI(String lang, String text, final ApiCallback callback) {
        Context context = LanguageTranslatorApplication.getContext();

        RequestQueue volleyRequestQueue = Volley.newRequestQueue(context);
        String url = TranslatorAPI.getTranslationUrl(text, lang);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(JsonObjectRequest.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("text");
                    String translation = array.getString(0);
                    callback.onSuccess(translation);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR", "An error occurred: " + error.getMessage());
            }
        });
        volleyRequestQueue.add(jsonRequest);
    }

    public List<String> getLanguagesByList() {
        HashMap<String, String> languagesMap = Language.getLanguagesFromLocalStorage();
        if (languagesMap != null) {
            List<String> languages = new ArrayList<>(languagesMap.values());
            Collections.sort(languages);
            return languages;
        }
        return null;
    }

    public HashMap<String, String> getLanguagesByMap() {
        return Language.getLanguagesFromLocalStorage();
    }

    private void initialiseDataStore() {
        Context context = LanguageTranslatorApplication.getContext();
        dataStore = new TranslationsDbHelper(context);
    }

    public interface ApiCallback {
        void onSuccess(String translation);
        void onError();
    }

}
