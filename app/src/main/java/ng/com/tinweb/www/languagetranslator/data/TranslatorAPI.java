package ng.com.tinweb.www.languagetranslator.data;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import ng.com.tinweb.www.languagetranslator.LanguageTranslatorApplication;
import ng.com.tinweb.www.languagetranslator.R;

/**
 * Created by kamiye on 08/09/2016.
 */
public class TranslatorAPI {

    private static Context context = LanguageTranslatorApplication.getContext();
    private static String apiKey = context.getResources().getString(R.string.translation_key);

    public static void translate(String input, String translationDirection, final TranslateCallback translateCallback) {
        RequestQueue volleyRequestQueue = Volley.newRequestQueue(context);
        String inputQuery = "&text=" + input;
        String translationQuery = "&lang=" + translationDirection;

        String queryString = getUrl() + inputQuery + translationQuery;

        JsonObjectRequest jsonRequest = new JsonObjectRequest(JsonObjectRequest.Method.GET,
                queryString, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                translateCallback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR", error.getMessage());
            }
        });
        volleyRequestQueue.add(jsonRequest);
    }

    public static void getLanguages(final GetLanguageCallback callback) {
        RequestQueue volleyRequestQueue = Volley.newRequestQueue(context);
        String queryUrl = "https://translate.yandex.net/api/v1.5/tr.json/getLangs?ui=en&key=" +
                apiKey;
        JsonObjectRequest jsonRequest = new JsonObjectRequest(JsonObjectRequest.Method.GET,
                queryUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // TODO handle response here
                try {
                    JSONObject languages = response.getJSONObject("langs");
                    callback.onSuccess(languages);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO handle error here
            }
        });
        volleyRequestQueue.add(jsonRequest);
    }

    private static String getUrl() {
        String baseUrl = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=";
        String key = context.getResources().getString(R.string.translation_key);

        return baseUrl + key;
    }

    public interface TranslateCallback {
        void onSuccess(JSONObject response);
        void onError();
    }

    public interface GetLanguageCallback {
        void onSuccess(JSONObject languages);
        void onError();
    }

}
