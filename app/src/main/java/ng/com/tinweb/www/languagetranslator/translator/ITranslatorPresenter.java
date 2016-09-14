package ng.com.tinweb.www.languagetranslator.translator;

import java.util.List;

import ng.com.tinweb.www.languagetranslator.data.ShakeDetector;

/**
 * Created by kamiye on 08/09/2016.
 */
public interface ITranslatorPresenter {
    void translateText(String text, String language);

    List<String> getLanguages();

    String getLanguageCode(String language);

    void setUpShakeDetector(ShakeDetector.OnShakeListener onShakeListener);
}
