package ng.com.tinweb.www.languagetranslator.data;


import com.google.gson.JsonObject;

import ng.com.tinweb.www.languagetranslator.LanguageTranslator;
import ng.com.tinweb.www.languagetranslator.R;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by kamiye on 08/09/2016.
 */
public class TranslatorAPI {


    public static final String API_KEY = LanguageTranslator.getContext()
            .getResources().getString(R.string.translation_key);

    private static final String API_BASE_URL = "https://translate.yandex.net/";

    public interface TranslationService {
        @GET("api/v1.5/tr.json/translate")
        Call<JsonObject> getTranslation(@Query("key") String key,
                                        @Query("text") String text,
                                        @Query("lang") String lang);

        @GET("api/v1.5/tr.json/getLangs?ui=en")
        Call<JsonObject> getLanguages(@Query("key") String key);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
