package ng.com.tinweb.www.languagetranslator;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ng.com.tinweb.www.languagetranslator.data.TranslatorAPI;
import ng.com.tinweb.www.languagetranslator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements TranslatorView,
        View.OnClickListener{

    private ActivityMainBinding activityBinding;
    private ITranslatorPresenter translatorPresenter;
    private String fromSelector = "en";
    private String toSelector = "en";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initialisePresenter();
        setUpSpinners();
        setUpTranslateAction();
    }

    private void initialisePresenter() {
        translatorPresenter = new TranslatorPresenter(this);
    }

    private void setUpSpinners() {
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.testValues, android.R.layout.simple_spinner_item);

        // TODO use a custom adapter here to make spinner selection in plain English

        activityBinding.fromSelectorSpinner.setAdapter(spinnerAdapter);
        activityBinding.toSelectorSpinner.setAdapter(spinnerAdapter);

        activityBinding.fromSelectorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fromSelector = (String) adapterView.getItemAtPosition(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        activityBinding.toSelectorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                toSelector = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

    private void setUpTranslateAction() {
        activityBinding.translateButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == activityBinding.translateButton) {
            // TODO get translation to be handled by presenter

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage(getString(R.string.translating));
            progressDialog.setIndeterminate(true);
            progressDialog.show();

            String input = activityBinding.fromInputEditText.getText().toString();
            String lang = fromSelector + "-" + toSelector;
            TranslatorAPI.translate(input, lang, new TranslatorAPI.TranslateCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    try {
                        JSONArray array = response.getJSONArray("text");
                        activityBinding.toOutputTextView.setText(array.getString(0));
                        progressDialog.hide();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError() {

                }
            });
        }
    }
}
