package uk.ac.qub.leaderelectiongame.async;

import android.os.AsyncTask;

import java.util.List;

import uk.ac.qub.leaderelectiongame.algorithm.LeaderElectionAlgorithm;
import uk.ac.qub.leaderelectiongame.consts.Consts;
import uk.ac.qub.leaderelectiongame.exceptions.LeaderElectionException;
import uk.ac.qub.leaderelectiongame.model.Node;
import uk.ac.qub.leaderelectiongame.model.PerformanceInput;
import uk.ac.qub.leaderelectiongame.model.PerformanceOutput;

public class PerformanceAsyncTask extends AsyncTask<PerformanceInput, String, PerformanceOutput> {

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
                publishProgress(String.format(Consts.PERFORMANCE_PROGRESS_INFO_INIT_NODES, i + 1));
                List<Node> nodesFromAlgorithm = LeaderElectionAlgorithm.initNodes(performanceInputs[0].getNetworkSize());
                publishProgress(String.format(Consts.PERFORMANCE_PROGRESS_INFO_GET_PARTICIPANTS, i + 1));
                nodesFromAlgorithm = LeaderElectionAlgorithm.getParticipants(nodesFromAlgorithm);
                result.addCandidateNumber(LeaderElectionAlgorithm.getCandidatesNumber());
                publishProgress(String.format(Consts.PERFORMANCE_PROGRESS_INFO_GET_REFEREES, i + 1));
                nodesFromAlgorithm = LeaderElectionAlgorithm.getReferees(nodesFromAlgorithm);
                publishProgress(String.format(Consts.PERFORMANCE_PROGRESS_INFO_NOMINATING_WINNERS, i + 1));
                LeaderElectionAlgorithm.findWinner(nodesFromAlgorithm);
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
