package uk.ac.qub.leaderelectiongame.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import uk.ac.qub.leaderelectiongame.R;
import uk.ac.qub.leaderelectiongame.algorithm.LeaderElectionAlgorithm;
import uk.ac.qub.leaderelectiongame.async.PerformanceAsyncTask;
import uk.ac.qub.leaderelectiongame.consts.Consts;
import uk.ac.qub.leaderelectiongame.helpers.PerformanceHelper;
import uk.ac.qub.leaderelectiongame.model.PerformanceInput;
import uk.ac.qub.leaderelectiongame.model.PerformanceOutput;


/**
 * Fragment responsible for performing performance check.
 */
public class AdvancedFragment extends Fragment {

    /**
     * Spinner to set network size.
     */
    private Spinner spinnerNetworkSize;

    /**
     * Spinner to set number of algorithm runs.
     */
    private Spinner spinnerAlgorithmRuns;

    /**
     * Scroll view with performance results.
     */
    private ScrollView svResults;

    /**
     * Results line.
     */
    private View resultsLine;

    /**
     * Button to run performance.
     */
    private Button btnRunPerformance;

    /**
     * Text view to display min cadidates percentage.
     */
    private TextView txtMinNodeCandidatesPercentage;

    /**
     * Text view to display max cadidates percentage.
     */
    private TextView txtMaxNodeCandidatesPercentage;

    /**
     * Text view to display avg cadidates percentage.
     */
    private TextView txtAvgNodeCandidatesPercentage;

    /**
     * Text view to display referees number.
     */
    private TextView txtRefereesNumber;

    /**
     * Text view to display total algorithm time.
     */
    private TextView txtTotalAlgorithmTime;

    /**
     * Text view to display min algorithm time.
     */
    private TextView txtMinAlgorithmTime;

    /**
     * Text view to display max algorithm time.
     */
    private TextView txtMaxAlgorithmTime;

    /**
     * Text view to display avg algorithm time.
     */
    private TextView txtAvgAlgorithmTime;

    /**
     * Text view to display network size.
     */
    private TextView txtNetworkSize;

    /**
     * Text view to display number of algorithm runs.
     */
    private TextView txtAlgorithmRuns;

    /**
     * Text view to display number of algorithm winners.
     */
    private TextView txtWinnersNumber;

    /**
     * Progress dialog.
     */
    private ProgressDialog pDialog;

    /**
     * Network size to performance check.
     */
    private int networkSize = Consts.ALGRITHM_PERFORMANCE_DEFAULT_NETWORK_SIZE;

    /**
     * Wake lock to acquire.
     */
    private PowerManager.WakeLock wakeLock = null;

    /**
     * Number of algorithm runs to performance check.
     */
    private int algorithmRuns = Consts.ALGRITHM_PERFORMANCE_DEFAULT_ALGORITHM_RUNS;

    /**
     * Required empty public constructor.
     */
    public AdvancedFragment() {
    }


    /**
     * Method to create and return the view hierarchy associated with the fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_advanced, container, false);

        //Initialising elements
        this.spinnerNetworkSize = (Spinner) v.findViewById(R.id.spinnerNetworkSize);
        this.spinnerNetworkSize = initSpinner(this.spinnerNetworkSize, R.array.advanced_network_size_arrays);
        this.spinnerAlgorithmRuns = (Spinner) v.findViewById(R.id.spinnerAlgorithmRuns);
        this.spinnerAlgorithmRuns = initSpinner(this.spinnerAlgorithmRuns, R.array.advanced_algorithm_run_arrays);
        this.svResults = (ScrollView) v.findViewById(R.id.svResults);
        this.resultsLine = v.findViewById(R.id.resultsLine);
        this.btnRunPerformance = (Button) v.findViewById(R.id.btnRunPerformance);
        this.txtMinNodeCandidatesPercentage = (TextView) v.findViewById(R.id.txtMinNodeCandidatesPercentage);
        this.txtMaxNodeCandidatesPercentage = (TextView) v.findViewById(R.id.txtMaxNodeCandidatesPercentage);
        this.txtAvgNodeCandidatesPercentage = (TextView) v.findViewById(R.id.txtAvgNodeCandidatesPercentage);
        this.txtRefereesNumber = (TextView) v.findViewById(R.id.txtRefereesNumber);
        this.txtTotalAlgorithmTime = (TextView) v.findViewById(R.id.txtTotalAlgorithmTime);
        this.txtMinAlgorithmTime = (TextView) v.findViewById(R.id.txtMinAlgorithmTime);
        this.txtMaxAlgorithmTime = (TextView) v.findViewById(R.id.txtMaxAlgorithmTime);
        this.txtAvgAlgorithmTime = (TextView) v.findViewById(R.id.txtAvgAlgorithmTime);
        this.txtNetworkSize = (TextView) v.findViewById(R.id.txtNetworkSize);
        this.txtAlgorithmRuns = (TextView) v.findViewById(R.id.txtAlgorithmRuns);
        this.txtWinnersNumber = (TextView) v.findViewById(R.id.txtWinnersNumber);

        //Events
        this.handleSpinnerEvents();
        this.handleButtonsEvents();

        //Wake lock
        if (getActivity() != null) {
            PowerManager powerManager = (PowerManager) getActivity().getSystemService(Context.POWER_SERVICE);
            this.wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, Consts.PERFORMANCE_WAKE_LOCK);
        }

        return v;
    }

    /**
     * Method to init spinner with values from string array.
     * @param spinner
     * @param arrayResItems
     * @return
     */
    private Spinner initSpinner(Spinner spinner, int arrayResItems) {
        if (spinner == null) {
            return null;
        }   //if
        if (getActivity() != null) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                    arrayResItems, R.layout.spinner_item);
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
            spinner.setAdapter(adapter);
            return spinner;
        }   //if
        return spinner;
    }

    /**
     * Method to handle spinner events.
     */
    private void handleSpinnerEvents() {
        this.spinnerAlgorithmRuns.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                try {
                    algorithmRuns = Integer.valueOf((String) parent.getItemAtPosition(pos));
                } catch (Exception ex) {
                    algorithmRuns = -1;
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
                //nothing for now
            }
        });
        this.spinnerNetworkSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                try {
                    networkSize = Integer.valueOf((String) parent.getItemAtPosition(pos));
                } catch (Exception ex) {
                    networkSize = -1;
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
                //nothing for now
            }
        });
    }

    /**
     * Method to validate performance check input params.
     * @return
     */
    private boolean validateAlgorithmParams() {
        return ((this.networkSize > 0) && (this.algorithmRuns > 0));
    }

    /**
     * Method to handle run performance button click.
     */
    private void handleButtonsEvents() {
        btnRunPerformance.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!validateAlgorithmParams()) {
                    if (getActivity() != null) {
                        Toast.makeText(getActivity(),
                                getString(R.string.improper_algorithm_params), Toast.LENGTH_LONG)
                                .show();
                    }   //if
                    return;
                }   //if
                runPerformance();
            }
        });
    }

    /**
     * Method to clear performance results.
     */
    private void clearPerformanceResults() {
        this.svResults.setVisibility(View.GONE);
        this.resultsLine.setVisibility(View.GONE);
        this.txtAlgorithmRuns.setText(Consts.BLANK_STRING_VALUE);
        this.txtNetworkSize.setText(Consts.BLANK_STRING_VALUE);
        this.txtMinNodeCandidatesPercentage.setText(Consts.BLANK_STRING_VALUE);
        this.txtMaxNodeCandidatesPercentage.setText(Consts.BLANK_STRING_VALUE);
        this.txtAvgNodeCandidatesPercentage.setText(Consts.BLANK_STRING_VALUE);
        this.txtRefereesNumber.setText(Consts.BLANK_STRING_VALUE);
        this.txtTotalAlgorithmTime.setText(Consts.BLANK_STRING_VALUE);
        this.txtMinAlgorithmTime.setText(Consts.BLANK_STRING_VALUE);
        this.txtMaxAlgorithmTime.setText(Consts.BLANK_STRING_VALUE);
        this.txtAvgAlgorithmTime.setText(Consts.BLANK_STRING_VALUE);
        this.txtWinnersNumber.setText(Consts.BLANK_STRING_VALUE);
    }

    /**
     * Method to display performance results.
     * @param output
     */
    private void showPerformanceResults(PerformanceOutput output) {
        if (output == null) {
            return;
        }   //if
        this.txtAlgorithmRuns.setText(String.valueOf(algorithmRuns));
        this.txtNetworkSize.setText(String.valueOf(networkSize));
        this.txtMinNodeCandidatesPercentage.setText(PerformanceHelper.calculateAndFormatMinCandidatesPercentage(output.getCandidatesNumbers(), this.networkSize));
        this.txtMaxNodeCandidatesPercentage.setText(PerformanceHelper.calculateAndFormatMaxCandidatesPercentage(output.getCandidatesNumbers(), this.networkSize));
        this.txtAvgNodeCandidatesPercentage.setText(PerformanceHelper.calculateAndFormatAvgCandidatesPercentage(output.getCandidatesNumbers(), this.networkSize));
        this.txtRefereesNumber.setText(Consts.INTEGER_FORMAT.format(LeaderElectionAlgorithm.calculateRefereesAmount(this.networkSize)));
        this.txtTotalAlgorithmTime.setText(String.format("%s ms", Consts.SECONDS_FORMAT.format(output.getAlgorithmTotalTimeInMilis())));
        this.txtMinAlgorithmTime.setText(PerformanceHelper.calculateAndFormatMinAlgorithmTime(output.getAlgoritmTimeInMilis()));
        this.txtMaxAlgorithmTime.setText(PerformanceHelper.calculateAndFormatMaxAlgorithmTime(output.getAlgoritmTimeInMilis()));
        this.txtAvgAlgorithmTime.setText(PerformanceHelper.calculateAndFormatAvgAlgorithmTime(output.getAlgoritmTimeInMilis()));
        this.txtWinnersNumber.setText(Consts.INTEGER_FORMAT.format(output.getWinnersNumber()));
        this.svResults.setVisibility(View.VISIBLE);
        this.resultsLine.setVisibility(View.VISIBLE);
    }

    /**
     * Method to acquire wake lock, run async task to run performance and release wake lock after performance check.
     */
    private void runPerformance() {
        clearPerformanceResults();
        PerformanceInput input = new PerformanceInput(networkSize, algorithmRuns);
        if (getActivity() != null) {
            new PerformanceAsyncTask(getActivity()) {
                @Override
                public void onPostExecute(PerformanceOutput result) {
                    if (wakeLock != null) {
                        wakeLock.release();
                    }   //if
                    hideDialog();
                    if (result == null) {
                        Toast.makeText(getActivity(),
                                getString(R.string.performance_test_error), Toast.LENGTH_LONG)
                                .show();
                        return;
                    }   //if
                    showPerformanceResults(result);
                }

                @Override
                protected void onPreExecute() {
                    if (wakeLock != null) {
                        wakeLock.acquire();
                    }   //if
                    showOrUpdateDialog(getString(R.string.performance_test_in_progress));
                }

                @Override
                protected void onProgressUpdate(String... values) {
                    if (values == null) {
                        return;
                    }   //if
                    if (values.length == 0) {
                        return;
                    }   //if
                    showOrUpdateDialog(values[0]);
                }
            }.execute(input);
        }   //if
    }

    /**
     * Method to show progress dialog or update progress dialog text.
     * @param message
     */
    private void showOrUpdateDialog(String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        if (pDialog == null) {
            if (getActivity() != null) {
                pDialog = new ProgressDialog(getActivity());
                pDialog.setCancelable(false);
            }   //if
            if (pDialog == null) {
                return;
            }   //if
        }   //if
        pDialog.setMessage(message);
        if (!pDialog.isShowing()) {
            pDialog.show();
        }   //if
    }

    /**
     * Method to hide progress dialog.
     */
    private void hideDialog() {
        if (pDialog == null) {
            return;
        }   //if
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }   //if
    }

}
