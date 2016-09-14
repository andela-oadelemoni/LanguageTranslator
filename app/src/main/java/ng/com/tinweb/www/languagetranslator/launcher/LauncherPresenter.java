package ng.com.tinweb.www.languagetranslator.launcher;

import android.util.Log;

import org.json.JSONObject;

import java.lang.ref.WeakReference;

import ng.com.tinweb.www.languagetranslator.data.language.Language;
import ng.com.tinweb.www.languagetranslator.data.language.LanguageDataStore;
import ng.com.tinweb.www.languagetranslator.data.TranslatorAPI;

/**
 * Created by kamiye on 14/09/2016.
 */
public class LauncherPresenter implements ILauncherPresenter {

    private WeakReference<ILauncherView> launcherView;
    private Language languageModel;

    public LauncherPresenter(ILauncherView launcherView) {
        this.launcherView = new WeakReference<>(launcherView);
        this.languageModel = new Language();
        getLanguages();
    }

    @Override
    public void removeAllLanguages() {
        languageModel.removeAll();
    }

    private void getLanguages() {
        final Language languageModel = new Language();
        // check if languages have been set
        if (!languageModel.isLanguagesSet()) {
            // get the languages
            TranslatorAPI.getLanguages(new TranslatorAPI.GetLanguageCallback() {
                @Override
                public void onSuccess(JSONObject languages) {
                    Log.i("STATUS", "getting languages was successful");
                    setLanguages(languageModel, languages);
                }

                @Override
                public void onError(String message) {
                    Log.i("STATUS", "error getting languages");
                    if (launcherView.get() != null) {
                        launcherView.get().onLanguageLoadingError(message);
                    }
                }
            });
        }
        else {
            Log.i("STATUS", "language already is set");
            if (launcherView.get() != null) {
                launcherView.get().onLanguageLoaded();
            }
        }
    }

    private void setLanguages(Language languageModel, JSONObject languages) {
        languageModel.setLanguages(languages, new LanguageDataStore.DbActionCallback() {
            @Override
            public void onFinish() {
                if (launcherView.get() != null) {
                    launcherView.get().onLanguageLoaded();
                }
            }
        });
    }

}
