package uk.ac.qub.leaderelectiongame.consts;

import java.text.DecimalFormat;

/**
 * Class responsible for grouping application constants.
 */
public class Consts {

    /**
     * Power of max node id.
     */
    public static final int MAX_ID_POWER = 4; //n^4

    /**
     * Step by step performance number.
     */
    public static final int PARTICIPANTS_NUMBER = 10;

    /**
     * Blank string value.
     */
    public static final String BLANK_STRING_VALUE = "----";

    /**
     * Yes string.
     */
    public static final String YES = "Yes";

    /**
     * No string.
     */
    public static final String NO = "No";

    /**
     * Algorithm performance error code.
     */
    public static final int ALGRITHM_PERFORMANCE_ERROR_CODE = -1;

    /**
     * One hundred.
     */
    public static final int ONE_HUNDRED = 100;

    /**
     * Default network size for performance check.
     */
    public static final int ALGRITHM_PERFORMANCE_DEFAULT_NETWORK_SIZE = 10;

    /**
     * Default algorithm runs number for performance check.
     */
    public static final int ALGRITHM_PERFORMANCE_DEFAULT_ALGORITHM_RUNS = 1;

    /**
     * Percentage decimal format.
     */
    public static final DecimalFormat PERCENTAGE_FORMAT = new DecimalFormat("#0.00####");

    /**
     * Integer decimal format.
     */
    public static final DecimalFormat INTEGER_FORMAT = new DecimalFormat("#,###,###,##0");

    /**
     * Seconds decimal format.
     */
    public static final DecimalFormat SECONDS_FORMAT = new DecimalFormat("#,###,###,###,###,##0");

    /**
     * Wake lock name for performance.
     */
    public static final String PERFORMANCE_WAKE_LOCK = "PerformanceWakeLock";

    /**
     * Cipher algorithm name for client - server communication.
     */
    public static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5PADDING";

    /**
     * Cipher algorithm key spec for client - server communication.
     */
    public static final String CIPHER_KEY_SPEC = "AES";

    /**
     * Cipher key for client - server communication.
     */
    public static final String CIPHER_KEY = "LeaderElectionGameCipherKey";

    /**
     * Internal message to close client - server communication.
     */
    public static final String MSG_CLOSE_CONNECTION = "qub.ac.uk.leaderelectiongame.CLOSE";

    /**
     * Good bye message for client - server communication.
     */
    public static final String MSG_ALGORITHM_FINISHED = "Algorithm finished. Thank you very much!";

    /**
     * English language code.
     */
    public static final String LANG_EN = "en";

    /**
     * Polish language code.
     */
    public static final String LANG_PL = "pl";

    /**
     * German language code.
     */
    public static final String LANG_DE = "de";

    /**
     * Russian language code.
     */
    public static final String LANG_RU = "ru";

}

