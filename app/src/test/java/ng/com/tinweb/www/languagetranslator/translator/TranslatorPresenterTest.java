package ng.com.tinweb.www.languagetranslator.translator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ng.com.tinweb.www.languagetranslator.data.TranslatorAPI;
import ng.com.tinweb.www.languagetranslator.data.translation.Translation;

import static org.mockito.Mockito.verify;

/**
 * Created by kamiye on 14/09/2016.
 */
public class TranslatorPresenterTest {

    @Mock
    private ITranslatorView translatorView;

    @Mock
    private Translation translationModel;

    @Captor
    private ArgumentCaptor<TranslatorAPI.TranslateCallback> translateCallback;

    private TranslatorPresenter translatorPresenter;

    @Before
    public void setUpTest() {
        MockitoAnnotations.initMocks(this);

        translatorPresenter = new TranslatorPresenter(translationModel, translatorView);
    }

    @Test
    public void testTranslateText() {
        String text = "I want to eat";
        String language = "en-fr";

        translatorPresenter.translateText(text, language);

        verify(translationModel).get(language, text, translatorPresenter);
    }

    @Test
    public void testGetLanguages() {
        translatorPresenter.getLanguages();

        verify(translationModel).getLanguagesByList();
    }

    @Test
    public void testGetLanguageCode() {
        translatorPresenter.getLanguageCode("English");

        verify(translationModel).getLanguagesByMap();
    }

}