package ng.com.tinweb.www.languagetranslator.data;

import android.provider.BaseColumns;

/**
 * Created by kamiye on 13/09/2016.
 */
public final class DbContract {

    private DbContract() {}

    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "Translator.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    public static final String SQL_CREATE_LANGUAGE_ENTRIES =
            "CREATE TABLE " + LanguagesSchema.TABLE_NAME + " (" +
                    LanguagesSchema._ID + " INTEGER PRIMARY KEY," +
                    LanguagesSchema.COLUMN_LANGUAGE_KEY + TEXT_TYPE + COMMA_SEP +
                    LanguagesSchema.COLUMN_LANGUAGE_VALUE + TEXT_TYPE + " )";

    public static final String SQL_DELETE_LANGUAGE_ENTRIES =
            "DROP TABLE IF EXISTS " + LanguagesSchema.TABLE_NAME;

    public static final String SQL_CREATE_TRANSLATIONS_ENTRIES =
            "CREATE TABLE " + SavedTranslationsSchema.TABLE_NAME + " (" +
                    SavedTranslationsSchema._ID + " INTEGER PRIMARY KEY," +
                    SavedTranslationsSchema.COLUMN_LANGUAGE + TEXT_TYPE + COMMA_SEP +
                    SavedTranslationsSchema.COLUMN_INPUT + TEXT_TYPE + COMMA_SEP +
                    SavedTranslationsSchema.COLUMN_OUTPUT + TEXT_TYPE + " )";

    public static final String SQL_DELETE_TRANSLATIONS_ENTRIES =
            "DROP TABLE IF EXISTS " + SavedTranslationsSchema.TABLE_NAME;

    public static class LanguagesSchema implements BaseColumns {
        public static final String TABLE_NAME = "languages";
        public static final String COLUMN_LANGUAGE_KEY = "key";
        public static final String COLUMN_LANGUAGE_VALUE = "value";
    }

    public static class SavedTranslationsSchema implements BaseColumns {
        public static final String TABLE_NAME = "saved_translations";
        public static final String COLUMN_LANGUAGE = "lang";
        public static final String COLUMN_INPUT = "input_text";
        public static final String COLUMN_OUTPUT = "output_text";
    }

}
