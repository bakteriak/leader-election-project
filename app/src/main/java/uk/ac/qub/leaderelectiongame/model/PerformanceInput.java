package uk.ac.qub.leaderelectiongame.model;

public class PerformanceInput {

    private final int networkSize;
    private final int algorithmRuns;

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
