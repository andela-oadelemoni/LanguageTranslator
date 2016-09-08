package ng.com.tinweb.www.languagetranslator;

import java.lang.ref.WeakReference;

/**
 * Created by kamiye on 08/09/2016.
 */
public class TranslatorPresenter implements ITranslatorPresenter {

    private WeakReference<TranslatorView> translatorView;

    public TranslatorPresenter (TranslatorView translatorView) {
        this.translatorView = new WeakReference<>(translatorView);
    }


    @Override
    public void translateText(String text, String language) {

    }
}
