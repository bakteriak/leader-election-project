package uk.ac.qub.leaderelectiongame.activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import uk.ac.qub.leaderelectiongame.fragment.AboutFragment;
import uk.ac.qub.leaderelectiongame.fragment.HomeFragment;
import uk.ac.qub.leaderelectiongame.fragment.LeaderGameFragment;
import uk.ac.qub.leaderelectiongame.R;
import uk.ac.qub.leaderelectiongame.fragment.ResearchPaperFragment;
import uk.ac.qub.leaderelectiongame.fragment.AdvancedFragment;
import uk.ac.qub.leaderelectiongame.fragment.StepByStepFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //private static final int REQUEST_PERMISSION = 10;

    private ResideMenu resideMenu;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemResearchPaper;
    private ResideMenuItem itemStepByStep;
    private ResideMenuItem itemLeaderGame;
    private ResideMenuItem itemAbout;
    private ResideMenuItem itemAdvanced;
    private Context mContext;
    private View buttonRender;
    private LinearLayout container;

    private RuntimePermissions runtimePermissions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;
        setUpMenu();

        if(savedInstanceState == null){
            changeFragment(new HomeFragment());
        }

        //RuntimePermissions runtimePermissions = new RuntimePermissions();

//        requestPermissions(new String[]{
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.READ_EXTERNAL_STORAGE,
//                Manifest.permission.INTERNET},
//                REQUEST_PERMISSION);
    }
   public void OnPermissionsGranted (int request_code){
        //Do anything when permisson granted
        Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_LONG).show();
    }

    private void setUpMenu() {
        //attach menu to current activity
        resideMenu = new ResideMenu(this);

        resideMenu.setBackground(R.drawable.menu_background);
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);

        //setting width of the menu using scale factor between 0.1 f to 1.0 f
        resideMenu.setScaleValue(0.6f);

        //adding menu items
        itemHome = new ResideMenuItem(this, R.drawable.icon_home, "Home");
        itemResearchPaper = new ResideMenuItem(this,R.drawable.icon_research,"Research Paper");
        itemAbout = new ResideMenuItem(this,R.drawable.icon_about,"About");
        itemLeaderGame = new ResideMenuItem(this,R.drawable.icon_leader_game,"Leader Game");
        itemStepByStep = new ResideMenuItem(this,R.drawable.icon_step_by_step,"Step By Step");
        itemAdvanced = new ResideMenuItem(this, R.drawable.icon_advanced, "Advanced");

        itemHome.setOnClickListener(this);
        itemResearchPaper.setOnClickListener(this);
        itemAbout.setOnClickListener(this);
        itemLeaderGame.setOnClickListener(this);
        itemStepByStep.setOnClickListener(this);
        itemAdvanced.setOnClickListener(this);

        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemResearchPaper,ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemAbout,ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemLeaderGame,ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemStepByStep,ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemAdvanced, ResideMenu.DIRECTION_RIGHT);

        //you can disable a side by -->
        //   resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

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

    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
            //Toast.makeText(mContext,"Menu is opened!",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void closeMenu() {
            //Toast.makeText(mContext, "Menu is closed!", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onClick(View v) {

        if(v == itemHome){
            changeFragment(new HomeFragment());
        }else if(v == itemResearchPaper){
            changeFragment(new ResearchPaperFragment());
            resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
            resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_LEFT);
        }else if(v == itemAbout){
            changeFragment(new AboutFragment());
        }else if(v == itemLeaderGame){
            changeFragment(new LeaderGameFragment());
        }else if(v == itemStepByStep){
            changeFragment(new StepByStepFragment());
        }else if(v == itemAdvanced){
            changeFragment(new AdvancedFragment());
        }

        resideMenu.closeMenu();
    }

    private void changeFragment(Fragment targetFragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment,targetFragment,"fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    public ResideMenu getResideMenu(){
        return resideMenu;
    }
}

