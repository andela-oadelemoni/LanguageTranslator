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

    private static final String TRANSLATION_BASE_URL = "https://translate.yandex.net/api/v1.5/tr.json/translate?key="
            + API_KEY;
    private static final String LANGUAGES_BASE_URL = "https://translate.yandex.net/api/v1.5/tr.json/getLangs?ui=en&key="
            + API_KEY;

    private static final String RETROFIT_URL = "https://translate.yandex.net/";
    private static final String GITHUB_API = "https://api.github.com/";

    public static String getTranslationUrl(String text, String language) {
        String inputText = "&text=" + text;
        String translationDirection = "&lang=" + language;

        return TRANSLATION_BASE_URL + inputText + translationDirection;
    }

    public static String getLanguagesUrl() {
        return LANGUAGES_BASE_URL;
    }

    public interface TranslationService {
        @GET("api/v1.5/tr.json/translate")
        Call<JsonObject> getTranslation(@Query("key") String key,
                                        @Query("text") String text,
                                        @Query("lang") String lang);

        @GET("api/v1.5/tr.json/getLangs?ui=en")
        Call<JsonObject> getLanguages(@Query("key") String key);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RETROFIT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
