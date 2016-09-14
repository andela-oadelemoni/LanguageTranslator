package ng.com.tinweb.www.languagetranslator.translator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import ng.com.tinweb.www.languagetranslator.data.TranslatorAPI;
import ng.com.tinweb.www.languagetranslator.data.language.Language;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by kamiye on 14/09/2016.
 */
public class TranslatorPresenterTest {

    @Mock
    private ITranslatorView translatorView;

    @Mock
    private Language languageModel;

    @Captor
    private ArgumentCaptor<TranslatorAPI.TranslateCallback> translateCallback;

    private TranslatorPresenter translatorPresenter;
    List<String> mockList = new ArrayList<>();

    @Before
    public void setUpTest() {
        MockitoAnnotations.initMocks(this);
        translatorPresenter = mock(TranslatorPresenter.class);
        mockList.add("Dutch");
        mockList.add("Swiss");
        when(translatorPresenter.getLanguages()).thenReturn(mockList);
        when(translatorPresenter.getLanguageCode("English")).thenReturn("en");
    }

    @Test
    public void testTranslateText() {
        String text = "I want to eat";
        String language = "en-fr";

        translatorPresenter.translateText(text, language);

        // TODO complete this test
    }

    @Test
    public void testGetLanguages() {
        List<String> languages = translatorPresenter.getLanguages();

        assertEquals(mockList, languages);
    }

    @Test
    public void testGetLanguageCode() {
        String langaugeCode = translatorPresenter.getLanguageCode("English");

        assertEquals("en", langaugeCode);
    }

}