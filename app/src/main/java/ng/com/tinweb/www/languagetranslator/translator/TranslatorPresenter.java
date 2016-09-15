package ng.com.tinweb.www.languagetranslator.translator;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;

import ng.com.tinweb.www.languagetranslator.data.ShakeDetector;
import ng.com.tinweb.www.languagetranslator.data.translation.Translation;

/**
 * Created by kamiye on 08/09/2016.
 */
public class TranslatorPresenter implements ITranslatorPresenter, Translation.ApiCallback {

    private WeakReference<ITranslatorView> translatorView;
    private Translation translationModel;

    public TranslatorPresenter (Translation translationModel, ITranslatorView ITranslatorView) {
        this.translatorView = new WeakReference<>(ITranslatorView);
        this.translationModel = translationModel;
    }

    @Override
    public void translateText(String text, String language) {
        translationModel.get(language, text, this);
    }

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

    @Override
    public List<String> getLanguages() {
        return translationModel.getLanguagesByList();
    }

    @Override
    public String getLanguageCode(String value) {
        HashMap<String, String> languages = translationModel.getLanguagesByMap();
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
