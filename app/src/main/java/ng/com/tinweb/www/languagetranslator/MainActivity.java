package ng.com.tinweb.www.languagetranslator;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import ng.com.tinweb.www.languagetranslator.data.Language;
import ng.com.tinweb.www.languagetranslator.data.ShakeDetector;
import ng.com.tinweb.www.languagetranslator.data.TranslatorAPI;
import ng.com.tinweb.www.languagetranslator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements TranslatorView,
        View.OnClickListener, AdapterView.OnItemSelectedListener, ShakeDetector.OnShakeListener {

    private ActivityMainBinding activityBinding;
    private ITranslatorPresenter translatorPresenter;
    private String fromSelector;
    private String toSelector;
    private HashMap<String, String> languages;
    private ArrayAdapter<String> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initialiseLanguageModel();
        initialisePresenter();
        setUpSpinners();
        setUpTranslateAction();
        setUpShakeDetector();
    }

    @Override
    public void onShake(int count) {
        Toast.makeText(this, "Device shaken by: " + count, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String value = (String) adapterView.getItemAtPosition(i);
        if (adapterView == activityBinding.fromSelectorSpinner) {
            fromSelector = getLanguageCode(languages, value);
        }
        if (adapterView == activityBinding.toSelectorSpinner) {
            toSelector = getLanguageCode(languages, value);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    @Override
    public void onClick(View view) {
        if (view == activityBinding.translateButton) {
            // TODO get translation to be handled by presenter
            String input = activityBinding.fromInputEditText.getText().toString();

            translateText(input);
        }
        if (view == activityBinding.switchTranslationImageView) {
            String fromSelected = (String) activityBinding.fromSelectorSpinner.getSelectedItem();
            String toSelected = (String) activityBinding.toSelectorSpinner.getSelectedItem();

            activityBinding.fromSelectorSpinner.setSelection(spinnerAdapter.getPosition(toSelected));
            activityBinding.toSelectorSpinner.setSelection(spinnerAdapter.getPosition(fromSelected));

            Toast.makeText(this, "Languages switched...", Toast.LENGTH_LONG).show();

            String input = activityBinding.toOutputTextView.getText().toString();
            activityBinding.fromInputEditText.setText(input);
            activityBinding.toOutputTextView.setText("");
        }
    }

    private void initialisePresenter() {
        translatorPresenter = new TranslatorPresenter(this);
    }

    private void setUpSpinners() {
        List<String> spinnerLanguages = new ArrayList<>(this.languages.values());
        Collections.sort(spinnerLanguages);
        spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, spinnerLanguages);

        activityBinding.fromSelectorSpinner.setAdapter(spinnerAdapter);
        activityBinding.toSelectorSpinner.setAdapter(spinnerAdapter);

        activityBinding.fromSelectorSpinner.setSelection(spinnerAdapter.getPosition(
                getString(R.string.defaultInputText)));
        activityBinding.toSelectorSpinner.setSelection(spinnerAdapter.getPosition(
                getString(R.string.defaultOutputText)));

        activityBinding.fromSelectorSpinner.setOnItemSelectedListener(this);
        activityBinding.toSelectorSpinner.setOnItemSelectedListener(this);
    }

    private void initialiseLanguageModel() {
        Language languageModel = new Language();
        languages = languageModel.getLanguages();
    }

    private String getLanguageCode(HashMap<String, String> map, String value) {
        for (String key : map.keySet()) {
            if (map.get(key).equals(value)) {
                return key;
            }
        }
        return null;
    }

    private void setUpTranslateAction() {
        activityBinding.translateButton.setOnClickListener(this);
        activityBinding.switchTranslationImageView.setOnClickListener(this);
    }

    private void translateText(String input) {
        String lang = fromSelector + "-" + toSelector;

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getString(R.string.translating));
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        TranslatorAPI.translate(input, lang, new TranslatorAPI.TranslateCallback() {
            @Override
            public void onSuccess(String translation) {
                activityBinding.toOutputTextView.setText(translation);
                progressDialog.hide();

            }

            @Override
            public void onError() {

            }
        });
    }

    private void setUpShakeDetector() {
        ShakeDetector shakeDetector = new ShakeDetector();
        shakeDetector.setOnShakeListener(this);
    }

    private void resetTranslation() {
        activityBinding.fromSelectorSpinner.setSelection(spinnerAdapter.getPosition(
                getString(R.string.defaultInputText)));
        activityBinding.toSelectorSpinner.setSelection(spinnerAdapter.getPosition(
                getString(R.string.defaultOutputText)));

        activityBinding.fromInputEditText.setText("");
        activityBinding.toOutputTextView.setText("");
    }

}
