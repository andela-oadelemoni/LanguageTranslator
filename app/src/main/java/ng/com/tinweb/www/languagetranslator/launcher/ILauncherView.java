package ng.com.tinweb.www.languagetranslator.launcher;

/**
 * Created by kamiye on 14/09/2016.
 */
public interface ILauncherView {
    void onLanguageLoaded();

    void onLanguageLoadingError(String errorMessage);
}
