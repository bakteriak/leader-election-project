package uk.ac.qub.leaderelectiongame.helpers;

import java.math.BigDecimal;
import java.util.List;

import uk.ac.qub.leaderelectiongame.consts.Consts;

public class PerformanceHelper {

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
