package ng.com.tinweb.www.languagetranslator;

import android.app.Application;
import android.content.Context;

/**
 * Created by kamiye on 08/09/2016.
 */
public class LanguageTranslatorApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;

        // TODO clean up old translations
    }

    public static Context getContext() {
        return context;
    }

}
