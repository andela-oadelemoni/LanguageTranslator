package ng.com.tinweb.www.languagetranslator.data;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import ng.com.tinweb.www.languagetranslator.LanguageTranslatorApplication;
import ng.com.tinweb.www.languagetranslator.R;

/**
 * Created by kamiye on 08/09/2016.
 */
public class TranslatorAPI {

    private static Context context = LanguageTranslatorApplication.getContext();

    public static void translate(String input, String translationDirection, final TranslateCallback translateCallback) {
        RequestQueue volleyRequestQueue = Volley.newRequestQueue(context);
        String inputQuery = "&text=" + input;
        String translationQuery = "&lang=" + translationDirection;

        String queryString = getUrl() + inputQuery + translationQuery;

        Log.i("OUTPUT", queryString);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(JsonObjectRequest.Method.GET,
                queryString, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                translateCallback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("OUTPUT", error.toString());
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

}
