package uk.ac.qub.leaderelectiongame.helpers;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import uk.ac.qub.leaderelectiongame.consts.Consts;

class MathHelper {

    public static int selectMinValue(List<Integer> values) {
        if (values == null) {
            return Consts.ALGRITHM_PERFORMANCE_ERROR_CODE;
        }   //if
        if (values.isEmpty()) {
            return Consts.ALGRITHM_PERFORMANCE_ERROR_CODE;
        }   //if
        try {
            return Collections.min(values);
        } catch (Exception ex) {
            return Consts.ALGRITHM_PERFORMANCE_ERROR_CODE;
        }   //catch
    }

    public static long selectLongMinValue(List<Long> values) {
        if (values == null) {
            return Consts.ALGRITHM_PERFORMANCE_ERROR_CODE;
        }   //if
        if (values.isEmpty()) {
            return Consts.ALGRITHM_PERFORMANCE_ERROR_CODE;
        }   //if
        try {
            return Collections.min(values);
        } catch (Exception ex) {
            return Consts.ALGRITHM_PERFORMANCE_ERROR_CODE;
        }   //catch
    }

    public static int selectMaxValue(List<Integer> values) {
        if (values == null) {
            return Consts.ALGRITHM_PERFORMANCE_ERROR_CODE;
        }   //if
        if (values.isEmpty()) {
            return Consts.ALGRITHM_PERFORMANCE_ERROR_CODE;
        }   //if
        try {
            return Collections.max(values);
        } catch (Exception ex) {
            return Consts.ALGRITHM_PERFORMANCE_ERROR_CODE;
        }   //catch
    }

    public static long selectLongMaxValue(List<Long> values) {
        if (values == null) {
            return Consts.ALGRITHM_PERFORMANCE_ERROR_CODE;
        }   //if
        if (values.isEmpty()) {
            return Consts.ALGRITHM_PERFORMANCE_ERROR_CODE;
        }   //if
        try {
            return Collections.max(values);
        } catch (Exception ex) {
            return Consts.ALGRITHM_PERFORMANCE_ERROR_CODE;
        }   //catch
    }

    public static BigDecimal calculatePercent(BigDecimal number, BigDecimal total) {
        if ((number == null) || (total == null)) {
            return new BigDecimal(Consts.ALGRITHM_PERFORMANCE_ERROR_CODE);
        }   //if
        try {
            return number.divide(total).multiply(new BigDecimal(Consts.ONE_HUNDRED));
        } catch (Exception ex) {
            return new BigDecimal(Consts.ALGRITHM_PERFORMANCE_ERROR_CODE);
        }   //catch
    }

    public static BigDecimal calculateLongAvg(List<Long> values) {
        if (values == null) {
            return new BigDecimal(Consts.ALGRITHM_PERFORMANCE_ERROR_CODE);
        }   //if
        try {
            BigDecimal sum = new BigDecimal(0);
            if (!values.isEmpty()) {
                for (Long value : values) {
                    sum = sum.add(new BigDecimal(value));
                }   //for
                return sum.divide(new BigDecimal(values.size()));
            }   //if
            return new BigDecimal(Consts.ALGRITHM_PERFORMANCE_ERROR_CODE);
        } catch (Exception ex) {
            return new BigDecimal(Consts.ALGRITHM_PERFORMANCE_ERROR_CODE);
        }   //catch
    }

    public static BigDecimal calculateAvgPercent(List<Integer> values, BigDecimal total) {
        if (values == null) {
            return new BigDecimal(Consts.ALGRITHM_PERFORMANCE_ERROR_CODE);
        }   //if
        try {
            BigDecimal sum = new BigDecimal(0);
            if (!values.isEmpty()) {
                for (Integer value : values) {
                    sum = sum.add(calculatePercent(new BigDecimal(value), total));
                }   //for
                return sum.divide(new BigDecimal(values.size()));
            }   //if
            return new BigDecimal(Consts.ALGRITHM_PERFORMANCE_ERROR_CODE);
        } catch (Exception ex) {
            return new BigDecimal(Consts.ALGRITHM_PERFORMANCE_ERROR_CODE);
        }   //catch
    }

}
