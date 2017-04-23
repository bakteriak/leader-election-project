package uk.ac.qub.leaderelectiongame.async;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import uk.ac.qub.leaderelectiongame.R;
import uk.ac.qub.leaderelectiongame.algorithm.LeaderElectionAlgorithm;
import uk.ac.qub.leaderelectiongame.consts.Consts;
import uk.ac.qub.leaderelectiongame.model.Node;
import uk.ac.qub.leaderelectiongame.model.PerformanceInput;
import uk.ac.qub.leaderelectiongame.model.PerformanceOutput;

public class PerformanceAsyncTask extends AsyncTask<PerformanceInput, String, PerformanceOutput> {

    private Context context;

    public PerformanceAsyncTask(Context context){
        this.context = context;
    }

    @Override
    protected PerformanceOutput doInBackground(PerformanceInput... performanceInputs) {
        try {
            if (performanceInputs == null) {
                return null;
            }   //if
            if (performanceInputs.length == 0) {
                return null;
            }   //if
            PerformanceOutput result = new PerformanceOutput();
            for (int i = 0; i < performanceInputs[0].getAlgorithmRuns(); i++) {
                long startTime = System.currentTimeMillis();
                publishProgress(String.format(context.getString(R.string.performance_progress_info_init_nodes), i + 1, performanceInputs[0].getAlgorithmRuns()));
                List<Node> nodesFromAlgorithm = LeaderElectionAlgorithm.initNodes(performanceInputs[0].getNetworkSize(), this.context);
                publishProgress(String.format(context.getString(R.string.performance_progress_info_get_participants), i + 1, performanceInputs[0].getAlgorithmRuns()));
                nodesFromAlgorithm = LeaderElectionAlgorithm.getParticipants(nodesFromAlgorithm);
                result.addCandidateNumber(LeaderElectionAlgorithm.getCandidatesNumber());
                publishProgress(String.format(context.getString(R.string.performance_progress_info_get_referees), i + 1, performanceInputs[0].getAlgorithmRuns()));
                nodesFromAlgorithm = LeaderElectionAlgorithm.getReferees(nodesFromAlgorithm);
                publishProgress(String.format(context.getString(R.string.performance_progress_info_nominating_winners), i + 1, performanceInputs[0].getAlgorithmRuns()));
                LeaderElectionAlgorithm.findWinner(nodesFromAlgorithm, this.context);
                result.incWinnersNumber(LeaderElectionAlgorithm.getWinnersNumber());
                long elapsedTime = System.currentTimeMillis() - startTime;
                result.addSingleIterationTimeInMilis(elapsedTime);
                result.incAlgorithmTotalTimeInMilis(elapsedTime);
            }   //for
            return result;
        } catch (Exception e) {
            return null;
        }
    }

}
