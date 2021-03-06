package uk.ac.qub.leaderelectiongame.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;

import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import uk.ac.qub.leaderelectiongame.R;
import uk.ac.qub.leaderelectiongame.fragment.AboutFragment;
import uk.ac.qub.leaderelectiongame.fragment.AdvancedFragment;
import uk.ac.qub.leaderelectiongame.fragment.HomeFragment;
import uk.ac.qub.leaderelectiongame.fragment.LeaderGameFragment;
import uk.ac.qub.leaderelectiongame.fragment.ResearchPaperFragment;
import uk.ac.qub.leaderelectiongame.fragment.StepByStepFragment;
import uk.ac.qub.leaderelectiongame.helpers.SettingsManager;

/**
 * Main activity, contains all actions in app, except home slider. Extends <code>BaseActivity</code>.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    /**
     * Reside menu object.
     */
    private ResideMenu resideMenu;

    /**
     * Reside menu home item.
     */
    private ResideMenuItem itemHome;

    /**
     * Reside menu research paper item.
     */
    private ResideMenuItem itemResearchPaper;

    /**
     * Reside menu step by step item.
     */
    private ResideMenuItem itemStepByStep;

    /**
     * Reside menu leader game item.
     */
    private ResideMenuItem itemLeaderGame;

    /**
     * Reside menu about item.
     */
    private ResideMenuItem itemAbout;

    /**
     * Reside menu performance item.
     */
    private ResideMenuItem itemAdvanced;

    /**
     * Reside menu help slider item.
     */
    private ResideMenuItem itemHelp;

    /**
     * Method being called to do initial creation of a activity
     * @param savedInstanceState - activity state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpMenu();
        if(savedInstanceState == null){
            changeFragment(new HomeFragment());
        }
    }

    /**
     * Method to set up menu and link menu items.
     */
    private void setUpMenu() {
        //attach menu to current activity
        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.menu_bg);
        resideMenu.attachToActivity(this);
        resideMenu.setScaleValue(0.6f);

        //adding menu items
        itemHome = new ResideMenuItem(this, R.drawable.icon_home, getString(R.string.menu_home));
        itemResearchPaper = new ResideMenuItem(this,R.drawable.icon_research, getString(R.string.menu_research_paper));
        itemAbout = new ResideMenuItem(this,R.drawable.icon_about, getString(R.string.menu_about));
        itemLeaderGame = new ResideMenuItem(this,R.drawable.icon_leader_game, getString(R.string.menu_leader_game));
        itemStepByStep = new ResideMenuItem(this,R.drawable.icon_step_by_step, getString(R.string.menu_step_by_step));
        itemAdvanced = new ResideMenuItem(this, R.drawable.icon_advanced, getString(R.string.menu_advanced));
        itemHelp = new ResideMenuItem(this, R.drawable.icon_help, getString(R.string.menu_help));

        itemHome.setOnClickListener(this);
        itemResearchPaper.setOnClickListener(this);
        itemAbout.setOnClickListener(this);
        itemLeaderGame.setOnClickListener(this);
        itemStepByStep.setOnClickListener(this);
        itemAdvanced.setOnClickListener(this);
        itemHelp.setOnClickListener(this);

        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemResearchPaper,ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemAbout, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemHelp, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemLeaderGame, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemStepByStep, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemAdvanced, ResideMenu.DIRECTION_RIGHT);

        findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
        findViewById(R.id.title_bar_right_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
            }
        });
    }

    /**
     * Method to handle touch events in activity.
     * @param ev
     * @return true if event consumed
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

    /**
     * Method to handle menu items click, mostly by changing fragment, also to show help slider.
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v == itemHome) {
            changeFragment(new HomeFragment());
        } else if(v == itemResearchPaper) {
            changeFragment(new ResearchPaperFragment());
        } else if(v == itemAbout) {
            changeFragment(new AboutFragment());
        } else if(v == itemLeaderGame){
            changeFragment(new LeaderGameFragment());
        } else if(v == itemStepByStep){
            changeFragment(new StepByStepFragment());
        } else if(v == itemAdvanced){
            changeFragment(new AdvancedFragment());
        } else if(v == itemHelp) {
            SettingsManager settingsManager = new SettingsManager(this);
            settingsManager.setShowHelpSlider(true);
            redirectToActivity(SliderActivity.class);
            finish();
        }
        resideMenu.closeMenu();
    }

    /**
     * Method to change current fragment.
     * @param targetFragment - fragment to transit to
     */
    private void changeFragment(Fragment targetFragment) {
        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment,targetFragment,"fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    /**
     * Method to redirecting to new activity.
     * @param clazz - class of activity to redirect to
     */
    private void redirectToActivity(Class clazz) {
        if (this.getClass() != clazz) {
            Intent intent = new Intent(this, clazz);
            startActivity(intent);
            finish();
        }   //if
    }

    /**
     * Reside menu getter.
     * @return menu object
     */
 public ResideMenu getResideMenu(){
        return resideMenu;
    }
}

