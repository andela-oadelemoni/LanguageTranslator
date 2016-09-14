package ng.com.tinweb.www.languagetranslator.translator;

/**
 * Created by kamiye on 08/09/2016.
 */
public interface ITranslatorView {

    void onTranslateSuccessful(String translation);

    void onTranslateError();
}
