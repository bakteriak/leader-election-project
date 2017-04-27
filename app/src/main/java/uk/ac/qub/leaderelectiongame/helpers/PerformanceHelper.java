package uk.ac.qub.leaderelectiongame.helpers;

import java.math.BigDecimal;
import java.util.List;

import uk.ac.qub.leaderelectiongame.consts.Consts;

/**
 * Class responsible for grouping performance results display functions used in application.
 */
public class PerformanceHelper {

    /**
     * Method to calculate and format min percent of candidates from performance check results.
     * @param candidatesNumbers
     * @param networkSize
     * @return min percent of candidates
     */
    public static String calculateAndFormatMinCandidatesPercentage(List<Integer> candidatesNumbers, int networkSize) {
        if (candidatesNumbers == null) {
            return Consts.BLANK_STRING_VALUE;
        }   //if
        int minValue = MathHelper.selectMinValue(candidatesNumbers);
        if (minValue == Consts.ALGRITHM_PERFORMANCE_ERROR_CODE) {
            return Consts.BLANK_STRING_VALUE;
        }   //if
        BigDecimal result = MathHelper.calculatePercent(new BigDecimal(minValue), new BigDecimal(networkSize));
        if (result.intValue() == Consts.ALGRITHM_PERFORMANCE_ERROR_CODE) {
            return Consts.BLANK_STRING_VALUE;
        }   //if
        return String.format("%s %%", Consts.PERCENTAGE_FORMAT.format(result.doubleValue()));
    }

    /**
     * Method to calculate and format max percent of candidates from performance check results.
     * @param candidatesNumbers
     * @param networkSize
     * @return max percent of candidates
     */
    public static String calculateAndFormatMaxCandidatesPercentage(List<Integer> candidatesNumbers, int networkSize) {
        if (candidatesNumbers == null) {
            return Consts.BLANK_STRING_VALUE;
        }   //if
        int maxValue = MathHelper.selectMaxValue(candidatesNumbers);
        if (maxValue == Consts.ALGRITHM_PERFORMANCE_ERROR_CODE) {
            return Consts.BLANK_STRING_VALUE;
        }   //if
        BigDecimal result = MathHelper.calculatePercent(new BigDecimal(maxValue), new BigDecimal(networkSize));
        if (result.intValue() == Consts.ALGRITHM_PERFORMANCE_ERROR_CODE) {
            return Consts.BLANK_STRING_VALUE;
        }   //if
        return String.format("%s %%", Consts.PERCENTAGE_FORMAT.format(result.doubleValue()));
    }

    /**
     * Method to calculate and format avg percent of candidates from performance check results.
     * @param candidatesNumbers
     * @param networkSize
     * @return avg percent of candidates
     */
    public static String calculateAndFormatAvgCandidatesPercentage(List<Integer> candidatesNumbers, int networkSize) {
        if (candidatesNumbers == null) {
            return Consts.BLANK_STRING_VALUE;
        }   //if
        BigDecimal result = MathHelper.calculateAvgPercent(candidatesNumbers, new BigDecimal(networkSize));
        if (result.intValue() == Consts.ALGRITHM_PERFORMANCE_ERROR_CODE) {
            return Consts.BLANK_STRING_VALUE;
        }   //if
        return String.format("%s %%", Consts.PERCENTAGE_FORMAT.format(result.doubleValue()));
    }

    /**
     * Method to calculate and format min algorith time from performance check results.
     * @param algorithmTimeInMilis
     * @return min algorithm time
     */
    public static String calculateAndFormatMinAlgorithmTime(List<Long> algorithmTimeInMilis) {
        if (algorithmTimeInMilis == null) {
            return Consts.BLANK_STRING_VALUE;
        }   //if
        double minValue = MathHelper.selectLongMinValue(algorithmTimeInMilis);
        if (minValue == Consts.ALGRITHM_PERFORMANCE_ERROR_CODE) {
            return Consts.BLANK_STRING_VALUE;
        }   //if
        return String.format("%s ms", Consts.SECONDS_FORMAT.format(minValue));
    }

    /**
     * Method to calculate and format max algorith time from performance check results.
     * @param algorithmTimeInMilis
     * @return max algorithm time
     */
    public static String calculateAndFormatMaxAlgorithmTime(List<Long> algorithmTimeInMilis) {
        if (algorithmTimeInMilis == null) {
            return Consts.BLANK_STRING_VALUE;
        }   //if
        double maxValue = MathHelper.selectLongMaxValue(algorithmTimeInMilis);
        if (maxValue == Consts.ALGRITHM_PERFORMANCE_ERROR_CODE) {
            return Consts.BLANK_STRING_VALUE;
        }   //if
        return String.format("%s ms", Consts.SECONDS_FORMAT.format(maxValue));
    }

    /**
     * Method to calculate and format average algorith time from performance check results.
     * @param algorithmTimeInMilis
     * @return avg algorithm time
     */
    public static String calculateAndFormatAvgAlgorithmTime(List<Long> algorithmTimeInMilis) {
        if (algorithmTimeInMilis == null) {
            return Consts.BLANK_STRING_VALUE;
        }   //if
        BigDecimal avgValue = MathHelper.calculateLongAvg(algorithmTimeInMilis);
        if (avgValue.intValue() == Consts.ALGRITHM_PERFORMANCE_ERROR_CODE) {
            return Consts.BLANK_STRING_VALUE;
        }   //if
        return String.format("%s ms", Consts.SECONDS_FORMAT.format(avgValue));
    }

}
