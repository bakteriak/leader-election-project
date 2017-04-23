package uk.ac.qub.leaderelectiongame.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import java.util.Locale;

import uk.ac.qub.leaderelectiongame.consts.Consts;
import uk.ac.qub.leaderelectiongame.helpers.SettingsManager;

public abstract class BaseActivity extends AppCompatActivity {

    private SettingsManager settingsManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLanguageLocale();
    }

    protected SettingsManager getSettingsManager() {
        if (this.settingsManager == null) {
            this.settingsManager = new SettingsManager(this);
        }   //if
        return this.settingsManager;
    }

    @SuppressWarnings("deprecation")
    protected void setSystemLocaleLegacy(Configuration config, Locale locale){
        config.locale = locale;
    }

    @TargetApi(Build.VERSION_CODES.N)
    protected void setSystemLocale(Configuration config, Locale locale){
        config.setLocale(locale);
    }

    protected void initLanguageLocale() {
        Configuration config = getBaseContext().getResources().getConfiguration();
        String languageCode = getSettingsManager().getLang();
        if (TextUtils.isEmpty(languageCode)) {
            languageCode = Consts.LANG_EN;
        }   //if
        Locale locale = new Locale(languageCode);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            setSystemLocale(config, locale);
        } else {
            setSystemLocaleLegacy(config, locale);
        }
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }

}
