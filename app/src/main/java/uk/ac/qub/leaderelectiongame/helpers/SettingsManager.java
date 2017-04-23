package uk.ac.qub.leaderelectiongame.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import uk.ac.qub.leaderelectiongame.consts.Consts;

public class SettingsManager {

    private final SharedPreferences pref;
    private final SharedPreferences.Editor editor;
    private final Context _context;

    // shared pref mode
    private final int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "uk.ac.qub.leaderelectiongame";

    private static final String SHOW_SLIDER = "ShowHelpSlider";
    private static final String LANG_KEY = "LangAbbr";

    public SettingsManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setShowHelpSlider(boolean showHelpSlider) {
        editor.putBoolean(SHOW_SLIDER, showHelpSlider);
        editor.commit();
    }

    public boolean showHelpSlider() {
        return pref.getBoolean(SHOW_SLIDER, true);
    }

    public String getLang() {
        return pref.getString(LANG_KEY, Consts.LANG_EN);
    }

    public void setLang(String abbr) {
        if (TextUtils.isEmpty(abbr)) {
            return;
        }   //if
        editor.putString(LANG_KEY, abbr);
        editor.commit();
    }

}
