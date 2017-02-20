package uk.ac.qub.leaderelectiongame.fragment;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import uk.ac.qub.leaderelectiongame.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {

    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_about, container, false);

        final TextView about_TextView = (TextView) v.findViewById(R.id.textViewAbout);
        final Button button_Text_About = (Button) v.findViewById(R.id.about_textButton);

        about_TextView.setTextSize(20);
        about_TextView.setText("The main aim of this project is to show the problem with leader election algorithm using Java development on Android devices. Algorithm used for this project will be randomized sublinear leader election which is distributed among the participants through a group of n nodes. \n" +
                "\n" +
                "Leader Election is one of the most fundamental problems in distributed systems. The idea is to select one of the nodes in the network as a leader and let it solve the critical problem of the day and then just convey the solution to the rest of the network. This problem first occur in token ring network introduced by Le Lann (1977). \n" +
                "\n" +
                "Leader election algorithm has 99% of correctness however there is great probability that it can fail in smaller networks. \n" +
                "\n" +
                "All nodes start with the same state not-elected. One node has to decide that is the leader. This then is given special status as a variable leader of the given n-nodes available. All the rest of the nodes on the network has to change their variable to non-leader. This nodes need to know the identity of the leader. \n" +
                "\n" +
                "If one node in the network resigns for some reason algorithm should recognise loss of the node and avoid the deadlock by removing this node from the network without breaking the cycle.\n" +
                "\n" +
                "This algorithm will use synchronous communication, each node can send only one message. Each node taking part will start up together at the same time upon execution. Some nodes would act as a referee and some other as a candidates. \n" +
                "\n" +
                "Every node can send and receive at the most one message to it neighbour however sent message has to be received in the same round only. In the first round each node taking part will become candidate. The referee will notify the candidate of win and it became the elected leader. \n" +
                "\n" +
                "The algorithms use a birthday paradox type argument to show that a unique candidate node wins all competitions with high probability.\n");
        button_Text_About.setBackgroundColor(0x00000000);
        button_Text_About.setTextSize(20);
        button_Text_About.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "copperplate_t_light.ttf"));

        button_Text_About.setTextColor(Color.parseColor("#0536b2"));
        button_Text_About.setText("Credits and special thanks \n\n\nto \n\n\nDr. Amitabh Trehan\n\n");
        button_Text_About.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick (View v) {

                Spannable textSpanAboutButton = new SpannableString("You have consistently maintained your \n" +
                        "noble stance of adding smiles to faces. \n\n" +
                        "Thank you so much for your \n\nguidance and useful advice.\n\n");
                button_Text_About.setTextColor(Color.parseColor("#050505"));
                button_Text_About.setBackgroundColor(0x000000CC);
                button_Text_About.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "gabriel-bolditalic.ttf"));
                button_Text_About.setTextSize(14);
                button_Text_About.setText(textSpanAboutButton);
            }});

        return v;
    }

}
