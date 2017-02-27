package uk.ac.qub.leaderelectiongame.consts;

public class Consts {

    public static final int MAX_ID_POWER = 4; //n^4
    public static final int PARTICIPANTS_NUMBER = 10;

    public static final String ALGORITHM_ERROR_TOO_MANY_PARTICIPANTS = "Too many participants (or max id too low).";
    public static final String ALGORITHM_ERROR_REFEREE_CANNOT_NOMINATE_WINNER = "Single referee unable to nominate winner...";
    public static final String ALGORITHM_ERROR_REFEREES_CANNOT_NOMINATE_WINNER = "All referees unable to nominate winner...";

    public static final String STEP_BY_STEP_STAGE_ONE_CAPTION = "Step one: Creating network";
    public static final String STEP_BY_STEP_STAGE_TWO_CAPTION = "Step two: Participants";
    public static final String STEP_BY_STEP_STAGE_THREE_CAPTION = "Step three: Referees";
    public static final String STEP_BY_STEP_STAGE_FOUR_CAPTION = "Step four: Election";

    public static final String STEP_BY_STEP_STAGE_ONE_EXPLANATION = "We created network with ten nodes, every node has a random id.";
    public static final String STEP_BY_STEP_STAGE_TWO_EXPLANATION = "All green nodes are taking part.";
    public static final String STEP_BY_STEP_STAGE_THREE_EXPLANATION = "Next each participant needed to choose referees. Nodes with blue border are referees.";
    public static final String STEP_BY_STEP_STAGE_FOUR_EXPLANATION = "Now we have our winner.";

    public static final String STEP_BY_STEP_STAGE_ONE_ALGORITHM_INFO = "Network size: %d. Each node generates a random rank from {1,...,pow(n,4)} = {1,...,%d}";
    public static final String STEP_BY_STEP_STAGE_TWO_ALGORITHM_INFO = "Every node decided to become a participant with probability ((2 * log n)/n) = %4.2f.";
    public static final String STEP_BY_STEP_STAGE_THREE_ALGORITHM_INFO = "Every participant samples 2 * ceil(sqrt(n * log10(n))) = %d other nodes (the referees) and sends a message with its own rank to each referee.";
    public static final String STEP_BY_STEP_STAGE_FOUR_ALGORITHM_INFO = "Referees nominated node with id: %s as winner.";

    public static final String BLANK_STRING_VALUE = "----";

    public static final String YES = "Yes";
    public static final String NO = "No";
}

