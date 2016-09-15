package ng.com.tinweb.www.languagetranslator.launcher;

import java.lang.ref.WeakReference;

import ng.com.tinweb.www.languagetranslator.data.language.Language;

/**
 * Created by kamiye on 14/09/2016.
 */
public class LauncherPresenter implements ILauncherPresenter, Language.ApiCallback {

    private WeakReference<ILauncherView> launcherView;
    private Language languageModel;

    public LauncherPresenter(Language language, ILauncherView launcherView) {
        this.launcherView = new WeakReference<>(launcherView);
        this.languageModel = language;
    }

    @Override
    public void removeAllLanguages() {
        languageModel.removeAll();
    }

    @Override
    public void onSuccess() {
        if (launcherView.get() != null) {
            launcherView.get().onLanguageLoaded();
        }
    }

    @Override
    public void onError(String message) {
        if (launcherView.get() != null) {
            launcherView.get().onLanguageLoadingError(message);
        }
    }

    @Override
    public void getLanguages() {
        languageModel.get(this);
    }

}
