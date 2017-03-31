package uk.ac.qub.leaderelectiongame.fragment;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.special.ResideMenu.ResideMenu;

import uk.ac.qub.leaderelectiongame.R;
import uk.ac.qub.leaderelectiongame.activity.MainActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    View v;
    ResideMenu resideMenu;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView about_TextView = (TextView) v.findViewById(R.id.textProjectName);
        about_TextView.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "copperplate_t_light.ttf"));

        setUpViews();
        return v;
    }
    private void setUpViews(){

        MainActivity parentActivity = (MainActivity)getActivity();
        resideMenu = parentActivity.getResideMenu();
        //v.
    }

}
