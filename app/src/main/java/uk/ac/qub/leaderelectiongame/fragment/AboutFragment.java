package uk.ac.qub.leaderelectiongame.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import uk.ac.qub.leaderelectiongame.R;

/**
 * Fragment responsible for displaying about app info.
 */
public class AboutFragment extends Fragment {

    /**
     * Required empty public constructor.
     */
    public AboutFragment() {
    }

    @Override
    /**
     * Method to create and return the view hierarchy associated with the fragment.
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

}
