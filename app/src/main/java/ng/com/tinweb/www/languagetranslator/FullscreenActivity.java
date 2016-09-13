package ng.com.tinweb.www.languagetranslator;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

import ng.com.tinweb.www.languagetranslator.data.Language;
import ng.com.tinweb.www.languagetranslator.data.LanguageDataStore;
import ng.com.tinweb.www.languagetranslator.data.LanguageDbHelper;
import ng.com.tinweb.www.languagetranslator.data.TranslatorAPI;

public class FullscreenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);
        getLanguages();

        //deleteLanguages();
    }

    private void deleteLanguages() {
        LanguageDbHelper dbHelper = new LanguageDbHelper(getApplicationContext());
        dbHelper.deleteLanguages();
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
                public void onError() {
                    Log.i("STATUS", "error getting languages");
                }
            });
        }
        else {
            Log.i("STATUS", "language already is set");
            launchMainActivity();
        }
    }

    private void setLanguages(Language languageModel, JSONObject languages) {
        languageModel.setLanguages(languages, new LanguageDataStore.DbActionCallback() {
            @Override
            public void onFinish() {
                launchMainActivity();
            }
        });
    }

    private void launchMainActivity() {
        Intent intent = new Intent(FullscreenActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
