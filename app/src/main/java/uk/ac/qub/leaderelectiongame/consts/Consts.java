package uk.ac.qub.leaderelectiongame.consts;

import java.text.DecimalFormat;

public class Consts {

    public static final int MAX_ID_POWER = 4; //n^4
    public static final int PARTICIPANTS_NUMBER = 10;

    public static final String BLANK_STRING_VALUE = "----";

    public static final String YES = "Yes";
    public static final String NO = "No";

    public static final int ALGRITHM_PERFORMANCE_ERROR_CODE = -1;
    public static final int ONE_HUNDRED = 100;

    public static final int ALGRITHM_PERFORMANCE_DEFAULT_NETWORK_SIZE = 10;
    public static final int ALGRITHM_PERFORMANCE_DEFAULT_ALGORITHM_RUNS = 1;

    public static final DecimalFormat PERCENTAGE_FORMAT = new DecimalFormat("#0.00####");
    public static final DecimalFormat INTEGER_FORMAT = new DecimalFormat("#,###,###,##0");
    public static final DecimalFormat SECONDS_FORMAT = new DecimalFormat("#,###,###,###,###,##0");

    public static final String PERFORMANCE_WAKE_LOCK = "PerformanceWakeLock";

    public static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5PADDING";
    public static final String CIPHER_KEY_SPEC = "AES";
    public static final String CIPHER_KEY = "LeaderElectionGameCipherKey";

    public static final String MSG_CLOSE_CONNECTION = "qub.ac.uk.leaderelectiongame.CLOSE";
    public static final String MSG_ALGORITHM_FINISHED = "Algorithm finished. Thank you very much!";

    public static final String LANG_EN = "en";
    public static final String LANG_PL = "pl";

    public static final String LANG_DE = "de";
    public static final String LANG_RU = "ru";
}

