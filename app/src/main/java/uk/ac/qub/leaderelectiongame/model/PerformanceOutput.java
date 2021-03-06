package uk.ac.qub.leaderelectiongame.model;

import java.util.ArrayList;
import java.util.List;

public class PerformanceOutput {

    private List<Integer> candidatesNumbers = new ArrayList<>();
    private int winnersNumber = 0;
    private List<Long> algoritmTimeInMilis = new ArrayList<>();
    private long algorithmTotalTimeInMilis = 0;

    public PerformanceOutput() {
        candidatesNumbers = new ArrayList<>();
        algoritmTimeInMilis = new ArrayList<>();
        algorithmTotalTimeInMilis = 0;
        winnersNumber = 0;
    }

    public List<Integer> getCandidatesNumbers() {
        return candidatesNumbers;
    }

    public List<Long> getAlgoritmTimeInMilis() {
        return algoritmTimeInMilis;
    }

    public long getAlgorithmTotalTimeInMilis() {
        return algorithmTotalTimeInMilis;
    }

    public void addSingleIterationTimeInMilis(long singleIterationTimeInMilis) {
        if (algoritmTimeInMilis == null) {
            algoritmTimeInMilis = new ArrayList<>();
        }   //if
        algoritmTimeInMilis.add(singleIterationTimeInMilis);
    }

    public void addCandidateNumber(int candidateNumber) {
        if (candidatesNumbers == null) {
            candidatesNumbers = new ArrayList<>();
        }   //if
        candidatesNumbers.add(candidateNumber);
    }

    public void incAlgorithmTotalTimeInMilis(long singleIterationTimeInMilis) {
        algorithmTotalTimeInMilis += singleIterationTimeInMilis;
    }

    public int getWinnersNumber() {
        return winnersNumber;
    }

    public void incWinnersNumber(int singleIterationWinnersNumber) {
        winnersNumber += singleIterationWinnersNumber;
    }

}
