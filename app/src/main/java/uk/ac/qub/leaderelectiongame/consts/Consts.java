package uk.ac.qub.leaderelectiongame.consts;

public class Consts {

    public static final int MAX_ID = 10000; //10 ^ 4
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

}
