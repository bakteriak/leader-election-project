package uk.ac.qub.leaderelectiongame.fragment;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import uk.ac.qub.leaderelectiongame.R;


/**
 * Fragment responsible for displaying home screen.
 */
public class HomeFragment extends Fragment {

    /**
     * Required empty public constructor.
     */
    public HomeFragment() {
    }


    /**
     * Method to create and return the view hierarchy associated with the fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView about_TextView = (TextView) v.findViewById(R.id.textProjectName);
        about_TextView.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "copperplate_t_light.ttf"));
        return v;
    }

}
