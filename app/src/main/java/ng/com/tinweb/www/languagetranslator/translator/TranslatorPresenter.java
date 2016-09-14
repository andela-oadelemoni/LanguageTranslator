package ng.com.tinweb.www.languagetranslator.translator;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import ng.com.tinweb.www.languagetranslator.data.language.Language;
import ng.com.tinweb.www.languagetranslator.data.ShakeDetector;
import ng.com.tinweb.www.languagetranslator.data.TranslatorAPI;

/**
 * Created by kamiye on 08/09/2016.
 */
public class TranslatorPresenter implements ITranslatorPresenter {

    private WeakReference<ITranslatorView> translatorView;
    private Language languageModel;
    private HashMap<String, String> languages;

    public TranslatorPresenter (ITranslatorView ITranslatorView) {
        this.translatorView = new WeakReference<>(ITranslatorView);
        languageModel = new Language();
    }


    @Override
    public void translateText(String text, String language) {

        TranslatorAPI.translate(text, language, new TranslatorAPI.TranslateCallback() {
            @Override
            public void onSuccess(String translation) {
                if (translatorView.get() != null) {
                    translatorView.get().onTranslateSuccessful(translation);
                }
            }

            @Override
            public void onError() {
                if (translatorView.get() != null) {
                    translatorView.get().onTranslateError();
                }
            }
        });
    }

    @Override
    public List<String> getLanguages() {
        languages = languageModel.getLanguages();
        List<String> languages = new ArrayList<>(this.languages.values());
        Collections.sort(languages);
        return languages;
    }

    @Override
    public String getLanguageCode(String value) {
        for (String key : languages.keySet()) {
            if (languages.get(key).equals(value)) {
                return key;
            }
        }
        return null;
    }

    @Override
    public void setUpShakeDetector(ShakeDetector.OnShakeListener onShakeListener) {
        ShakeDetector shakeDetector = new ShakeDetector();
        shakeDetector.setOnShakeListener(onShakeListener);
    }

}
