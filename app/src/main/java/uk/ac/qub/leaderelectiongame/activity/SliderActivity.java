package uk.ac.qub.leaderelectiongame.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Locale;

import uk.ac.qub.leaderelectiongame.R;
import uk.ac.qub.leaderelectiongame.consts.Consts;
import uk.ac.qub.leaderelectiongame.helpers.SettingsManager;


public class SliderActivity extends BaseActivity {

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnSkip, btnNext, btnChangeLanguage;

    private Spinner languageSpinner;
    private LinearLayout llLanguges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getSettingsManager().showHelpSlider()) {
            launchHomeScreen();
            finish();
        }

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_slider);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnSkip = (Button) findViewById(R.id.btnSkip);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnChangeLanguage = (Button) findViewById(R.id.btnChangeLanguage);
        languageSpinner = (Spinner) findViewById(R.id.spinnerLanguages);
        llLanguges = (LinearLayout) findViewById(R.id.llLanguages);

        initSpinner(languageSpinner, R.array.language_select);
        handleButtonEvents();

        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = new int[]{
                R.layout.slider_slide1,
                R.layout.slider_slide2,
                R.layout.slider_slide3,
                R.layout.slider_slide4,
                R.layout.slider_slide5,
                R.layout.slider_slide6};

        // adding bottom dots
        addBottomDots(0);

        // making notification bar transparent
        //changeStatusBarColor();

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHomeScreen();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(+1);
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    launchHomeScreen();
                }
            }
        });
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
        getSettingsManager().setShowHelpSlider(false);
        startActivity(new Intent(SliderActivity.this, MainActivity.class));
        finish();
    }

    //  viewpager change listener
    private final ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            if (position == 0) {
                llLanguges.setVisibility(View.VISIBLE);
            } else {  //if
                llLanguges.setVisibility(View.GONE);
            }   //else
            addBottomDots(position);
            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
                btnNext.setText(getString(R.string.start));
                btnSkip.setVisibility(View.GONE);
            } else {
                // still pages are left
                btnNext.setText(getString(R.string.next));
                btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String source) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(source);
        }
    }

    private Spinner initSpinner(Spinner spinner, int arrayResItems) {
        if (spinner == null) {
            return null;
        }   //if
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                arrayResItems, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
        String lang = getSettingsManager().getLang();
        if (lang.equalsIgnoreCase(Consts.LANG_PL)) {
            spinner.setSelection(1);
        }   //if
        return spinner;
    }

    private void handleButtonEvents() {
        btnChangeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int langIndex = languageSpinner.getSelectedItemPosition();
                    switch(langIndex) {
                        case 0: //English
                            getSettingsManager().setLang(Consts.LANG_EN);
                            recreate();
                            return;
                        case 1: //Polish
                            getSettingsManager().setLang(Consts.LANG_PL);
                            recreate();
                            return;
                        case 2: //German
                            getSettingsManager().setLang(Consts.LANG_DE);
                            recreate();
                            return;
                        case 3: //German
                            getSettingsManager().setLang(Consts.LANG_RU);
                            recreate();
                            return;
                        default: //By default set to English
                            getSettingsManager().setLang(Consts.LANG_EN);
                            recreate();
                            return;
                    }
                } catch (Exception ex) {
                    //do nothing
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
                //nothing for now
            }
        });
    }

}
