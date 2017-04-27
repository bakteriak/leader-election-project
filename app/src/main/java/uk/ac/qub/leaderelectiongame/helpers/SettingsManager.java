package uk.ac.qub.leaderelectiongame.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import uk.ac.qub.leaderelectiongame.consts.Consts;

/**
 * Class responsible for shared preferences handling.
 */
public class SettingsManager {

    /**
     * {@link SharedPreferences}
     */
    private final SharedPreferences pref;

    /**
     * {@link SharedPreferences.Editor}
     */
    private final SharedPreferences.Editor editor;

    /**
     * {@link Context}
     */
    private final Context _context;

    /**
     * Shared preferences private mode.
     */
    private final int PRIVATE_MODE = 0;

    /**
     * Shared preferences name.
     */
    private static final String PREF_NAME = "uk.ac.qub.leaderelectiongame";

    /**
     * Shared preferences - show slider option key.
     */
    private static final String SHOW_SLIDER = "ShowHelpSlider";

    /**
     * Shared preferences - current app language key.
     */
    private static final String LANG_KEY = "LangAbbr";

    /**
     * Constructor.
     * @param context
     */
    public SettingsManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Store show slider option value.
     * @param showHelpSlider
     */
    public void setShowHelpSlider(boolean showHelpSlider) {
        editor.putBoolean(SHOW_SLIDER, showHelpSlider);
        editor.commit();
    }

    /**
     * Get show slider option value.
     * @return true when slider needs to be shown, false otherwise
     */
    public boolean showHelpSlider() {
        return pref.getBoolean(SHOW_SLIDER, true);
    }

    /**
     * Get current language value.
     * @return current language abbreviation
     */
    public String getLang() {
        return pref.getString(LANG_KEY, Consts.LANG_EN);
    }

    /**
     * Store current language value.
     * @param abbr
     */
    public void setLang(String abbr) {
        if (TextUtils.isEmpty(abbr)) {
            return;
        }   //if
        editor.putString(LANG_KEY, abbr);
        editor.commit();
    }

}
