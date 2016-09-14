package ng.com.tinweb.www.languagetranslator.launcher;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ng.com.tinweb.www.languagetranslator.translator.MainActivity;
import ng.com.tinweb.www.languagetranslator.R;
import ng.com.tinweb.www.languagetranslator.databinding.ActivityFullscreenBinding;

public class FullscreenActivity extends AppCompatActivity implements ILauncherView {

    private ILauncherPresenter launcherPresenter;
    private ActivityFullscreenBinding activityBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_fullscreen);
        setUpPresenter();

        //deleteLanguages();
    }

    @Override
    public void onLanguageLoaded() {
        launchMainActivity();
    }

    @Override
    public void onLanguageLoadingError(String errorMessage) {
        activityBinding.errorMessage.setVisibility(View.VISIBLE);
        activityBinding.button.setVisibility(View.VISIBLE);
        activityBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchMainActivity();
            }
        });
    }

    private void setUpPresenter() {
        launcherPresenter = new LauncherPresenter(this);
    }

    private void deleteLanguages() {
        launcherPresenter.removeAllLanguages();
    }

    private void launchMainActivity() {
        Intent intent = new Intent(FullscreenActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
