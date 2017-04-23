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
import uk.ac.qub.leaderelectiongame.async.NetworkCheckAsyncTask;
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
        communicateNetworkInfo();
        initApp();
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
        if (getActivity() != null) {
            gameConnectionAsyncTask = new GameConnectionAsyncTask(getActivity()) {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    disableButton();
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    enableButton();
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
        }   //if
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

    private void initApp() {
        new NetworkCheckAsyncTask()
        {
            @Override
            public void onPostExecute(Boolean result) {
                if (result == null) {
                    communicateNetworkError();
                    return;
                }
                if (!result) {
                    communicateNetworkError();
                    return;
                }   //if
                communicateNetworkOk();
                handleButtonsEvents();
                enableButton();
            }

        }.execute();
    }

    private void communicateNetworkError() {
        messages.add(getString(R.string.network_connection_error));
        gameAdapter.notifyDataSetChanged();
    }

    private void communicateNetworkInfo() {
        messages.add(getString(R.string.network_connection_info));
        gameAdapter.notifyDataSetChanged();
    }

    private void communicateNetworkOk() {
        messages.add(getString(R.string.network_connection_ok));
        gameAdapter.notifyDataSetChanged();
    }

    private void disableButton() {
        btnNewGame.setEnabled(false);
        btnNewGame.setClickable(false);
        btnNewGame.setBackgroundResource(R.drawable.btn_shape_disabled);
        btnNewGame.setText(getString(R.string.button_game_in_progress));
    }

    private void enableButton() {
        btnNewGame.setEnabled(true);
        btnNewGame.setClickable(true);
        btnNewGame.setBackgroundResource(R.drawable.btn_shape);
        btnNewGame.setText(getString(R.string.button_new_game));
    }

}
