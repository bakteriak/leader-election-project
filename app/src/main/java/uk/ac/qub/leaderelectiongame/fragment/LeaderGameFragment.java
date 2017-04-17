package uk.ac.qub.leaderelectiongame.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import uk.ac.qub.leaderelectiongame.R;
import uk.ac.qub.leaderelectiongame.async.GameConnectionAsyncTask;
import uk.ac.qub.leaderelectiongame.game.GameAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class LeaderGameFragment extends Fragment {

    //layout elements
    private ListView lvGameList;
    private Button btnNewGame;

    private ArrayList<String> messages;
    private GameAdapter gameAdapter;

    private AsyncTask<Void, String, Void> gameConnectionAsyncTask;

    public LeaderGameFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_leader_game, container, false);
        lvGameList = (ListView) v.findViewById(R.id.lvGameList);
        btnNewGame = (Button) v.findViewById(R.id.btnNewGame);
        messages = new ArrayList<>();
        if (getActivity() != null) {
            gameAdapter = new GameAdapter(getActivity(), messages);
            lvGameList.setAdapter(gameAdapter);
        }   //if
        handleButtonsEvents();
        return v;
    }

    private void handleButtonsEvents() {
        btnNewGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                connect();
            }
        });
    }

    private void connect() {
        stopConnectionTask();
        gameConnectionAsyncTask = new GameConnectionAsyncTask()
        {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                btnNewGame.setEnabled(false);
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                btnNewGame.setEnabled(true);
            }

            @Override
            protected void onProgressUpdate(String... values) {
                if (values == null) {
                    return;
                }   //if
                if (values.length == 0) {
                    return;
                }   //if
                messages.add(values[0]);
                gameAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

    @Override
    public void onStop() {
        super.onStop();
        stopConnectionTask();
    }

    private void stopConnectionTask() {
        if ((gameConnectionAsyncTask != null) && (gameConnectionAsyncTask.getStatus() == AsyncTask.Status.RUNNING)) {
            gameConnectionAsyncTask.cancel(true);
        }   //if
    }
}
