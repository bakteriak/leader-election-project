package uk.ac.qub.leaderelectiongame.model;

import uk.ac.qub.leaderelectiongame.consts.Consts;

public class PerformanceInput {

    private int networkSize;
    private int algorithmRuns;

    public PerformanceInput(int networkSize, int algorithmRuns) {
        this.networkSize = networkSize;
        this.algorithmRuns = algorithmRuns;
    }

    public int getNetworkSize() {
        return networkSize;
    }

    public int getAlgorithmRuns() {
        return algorithmRuns;
    }
}
