package ng.com.tinweb.www.languagetranslator;

import ng.com.tinweb.www.languagetranslator.data.translation.Translation;

/**
 * Created by kamiye on 15/09/2016.
 */
public class Injection {

    public static Translation getTranslationModel() {
        return new Translation();
    }
}
