package ng.com.tinweb.www.languagetranslator.launcher;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ng.com.tinweb.www.languagetranslator.data.language.Language;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

/**
 * Created by kamiye on 15/09/2016.
 */
public class LauncherPresenterTest {

    @Mock
    private ILauncherView launcherView;

    @Mock
    private Language languageModel;

    private LauncherPresenter launcherPresenter;


    @Before
    public void setUpTest() {
        MockitoAnnotations.initMocks(this);

        launcherPresenter = new LauncherPresenter(languageModel, launcherView);
    }

    @Test
    public void testRemoveAllLanguages() {
        launcherPresenter.removeAllLanguages();

        verify(languageModel).removeAll();
    }

    @Test
    public void testGetLanguages() {
        launcherPresenter.getLanguages();

        verify(languageModel).get(launcherPresenter);
    }

    @Test
    public void testLanguageSuccessCallback() {
        launcherPresenter.onSuccess();

        verify(launcherView).onLanguageLoaded();
    }

    @Test
    public void testLanguageErrorCallback() {
        launcherPresenter.onError("Message");

        verify(launcherView).onLanguageLoadingError("Message");
    }

}