package ng.com.tinweb.www.languagetranslator.translator;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.List;

import ng.com.tinweb.www.languagetranslator.R;
import ng.com.tinweb.www.languagetranslator.data.ShakeDetector;
import ng.com.tinweb.www.languagetranslator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements ITranslatorView,
        View.OnClickListener, AdapterView.OnItemSelectedListener, ShakeDetector.OnShakeListener {

    private ActivityMainBinding activityBinding;
    private ITranslatorPresenter translatorPresenter;

    private String inputLanguage;
    private String outputLanguage;
    private CustomSpinnerAdapter spinnerAdapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initialisePresenter();
        setUpSpinners();
        setUpTranslateAction();
        setUpShakeDetector();

        setUpProgressDialog();
    }

    @Override
    public void onShake(int count) {
        Toast.makeText(this, "Device shaken by: " + count, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String value = (String) adapterView.getItemAtPosition(i);
        if (adapterView == activityBinding.fromSelectorSpinner) {
            inputLanguage = translatorPresenter.getLanguageCode(value);
        }
        if (adapterView == activityBinding.toSelectorSpinner) {
            outputLanguage = translatorPresenter.getLanguageCode(value);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    @Override
    public void onClick(View view) {
        if (view == activityBinding.translateButton) {

            String text = activityBinding.fromInputEditText.getText().toString();
            String language = inputLanguage + "-" + outputLanguage;

            // show progress dialog
            progressDialog.show();
            translatorPresenter.translateText(text, language);
        }
        if (view == activityBinding.switchTranslationImageView) {
            String fromSelected = (String) activityBinding.fromSelectorSpinner.getSelectedItem();
            String toSelected = (String) activityBinding.toSelectorSpinner.getSelectedItem();

            activityBinding.fromSelectorSpinner.setSelection(spinnerAdapter.getPosition(toSelected));
            activityBinding.toSelectorSpinner.setSelection(spinnerAdapter.getPosition(fromSelected));

            Toast.makeText(this, R.string.languages_switched, Toast.LENGTH_LONG).show();

            String input = activityBinding.toOutputTextView.getText().toString();
            activityBinding.fromInputEditText.setText(input);
            activityBinding.toOutputTextView.setText("");
        }
    }

    @Override
    public void onTranslateSuccessful(String translation) {
        activityBinding.toOutputTextView.setText(translation);
        progressDialog.hide();
    }

    @Override
    public void onTranslateError() {
        // TODO show error message here
        progressDialog.hide();
    }

    private void initialisePresenter() {
        translatorPresenter = new TranslatorPresenter(this);
    }

    private void setUpSpinners() {
        List<String> spinnerLanguages = translatorPresenter.getLanguages();

        spinnerAdapter = new CustomSpinnerAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, spinnerLanguages);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        activityBinding.fromSelectorSpinner.setAdapter(spinnerAdapter);
        activityBinding.toSelectorSpinner.setAdapter(spinnerAdapter);

        activityBinding.fromSelectorSpinner.setSelection(spinnerAdapter.getPosition(
                getString(R.string.defaultInputText)));
        activityBinding.toSelectorSpinner.setSelection(spinnerAdapter.getPosition(
                getString(R.string.defaultOutputText)));

        activityBinding.fromSelectorSpinner.setOnItemSelectedListener(this);
        activityBinding.toSelectorSpinner.setOnItemSelectedListener(this);
    }

    private void setUpTranslateAction() {
        activityBinding.translateButton.setOnClickListener(this);
        activityBinding.switchTranslationImageView.setOnClickListener(this);
    }

    private void setUpProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getString(R.string.translating));
        progressDialog.setIndeterminate(true);
    }

    private void setUpShakeDetector() {
        translatorPresenter.setUpShakeDetector(this);
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
