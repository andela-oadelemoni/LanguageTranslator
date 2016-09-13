package ng.com.tinweb.www.languagetranslator.data;

import android.provider.BaseColumns;

/**
 * Created by kamiye on 13/09/2016.
 */
public final class DbContract {

    private DbContract() {}

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + LanguagesSchema.TABLE_NAME + " (" +
                    LanguagesSchema._ID + " INTEGER PRIMARY KEY," +
                    LanguagesSchema.COLUMN_LANGUAGE_KEY + TEXT_TYPE + COMMA_SEP +
                    LanguagesSchema.COLUMN_LANGUAGE_VALUE + TEXT_TYPE + " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + LanguagesSchema.TABLE_NAME;

    public static class LanguagesSchema implements BaseColumns {
        public static final String TABLE_NAME = "languages";
        public static final String COLUMN_LANGUAGE_KEY = "key";
        public static final String COLUMN_LANGUAGE_VALUE = "value";
    }

}
