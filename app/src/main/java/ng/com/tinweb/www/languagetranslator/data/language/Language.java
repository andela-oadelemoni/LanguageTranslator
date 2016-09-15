package ng.com.tinweb.www.languagetranslator.data.language;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import ng.com.tinweb.www.languagetranslator.LanguageTranslatorApplication;
import ng.com.tinweb.www.languagetranslator.data.TranslatorAPI;

/**
 * Created by kamiye on 13/09/2016.
 */
public class Language {

    private static LanguageDataStore dataStore;

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
        Context context = LanguageTranslatorApplication.getContext();

        RequestQueue volleyRequestQueue = Volley.newRequestQueue(context);
        String url = TranslatorAPI.getLanguagesUrl();

        JsonObjectRequest jsonRequest = new JsonObjectRequest(JsonObjectRequest.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject languages = response.getJSONObject("langs");

                    setLanguages(languages, new LanguageDataStore.DbActionCallback() {
                        @Override
                        public void onFinish() {
                            callback.onSuccess();
                        }
                    });
//                    callback.onSuccess(languages);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error.getMessage());
            }
        });
        volleyRequestQueue.add(jsonRequest);
    }

    public void removeAll() {
        initialiseDataStore();
        dataStore.deleteLanguages();
    }

    public static HashMap<String, String> getLanguagesFromLocalStorage() {
        initialiseDataStore();
        return dataStore.getAllLanguages();
    }

    private static void initialiseDataStore() {
        dataStore = LanguageDbHelper.getInstance();
    }

    public interface ApiCallback {
        void onSuccess();
        void onError(String message);
    }
}
