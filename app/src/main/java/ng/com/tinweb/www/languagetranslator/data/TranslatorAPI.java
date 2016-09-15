package ng.com.tinweb.www.languagetranslator.data;

import android.content.Context;

import ng.com.tinweb.www.languagetranslator.LanguageTranslatorApplication;
import ng.com.tinweb.www.languagetranslator.R;

/**
 * Created by kamiye on 08/09/2016.
 */
public class TranslatorAPI {

    private static Context context = LanguageTranslatorApplication.getContext();
    private static String apiKey = context.getResources().getString(R.string.translation_key);

    public static String getTranslationUrl(String text, String language) {
        String inputText = "&text=" + text;
        String translationDirection = "&lang=" + language;
        String baseUrl = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=";

        return baseUrl + apiKey + inputText + translationDirection;
    }

    public static String getLanguagesUrl() {
        return "https://translate.yandex.net/api/v1.5/tr.json/getLangs?ui=en&key=" +
                apiKey;
    }

}
