package uk.ac.qub.leaderelectiongame.enums;


import uk.ac.qub.leaderelectiongame.consts.Consts;

public enum StepByStepStage {

    FIRST(1,
            Consts.STEP_BY_STEP_STAGE_ONE_CAPTION,
            Consts.STEP_BY_STEP_STAGE_ONE_EXPLANATION),
    SECOND(2,
            Consts.STEP_BY_STEP_STAGE_TWO_CAPTION,
            Consts.STEP_BY_STEP_STAGE_TWO_EXPLANATION),
    THIRD(3,
            Consts.STEP_BY_STEP_STAGE_THREE_CAPTION,
            Consts.STEP_BY_STEP_STAGE_THREE_EXPLANATION),
    FOURTH(4,
            Consts.STEP_BY_STEP_STAGE_FOUR_CAPTION,
            Consts.STEP_BY_STEP_STAGE_FOUR_EXPLANATION);

    private final String caption;
    private final String info;
    private final int number;

    private StepByStepStage(int number, String caption, String info) {
        this.number = number;
        this.caption = caption;
        this.info = info;
    }

    public String getCaption() {
        return caption;
    }

    public String getInfo() {
        return info;
    }

    public int getNumber() {
        return number;
    }

    public StepByStepStage getNextStage() {
        switch (this) {
            case FIRST:
                return SECOND;
            case SECOND:
                return THIRD;
            case THIRD:
                return FOURTH;
            case FOURTH:
                return FOURTH;
        }
        return FIRST;
    }

    public StepByStepStage getPrevStage() {
        switch (this) {
            case FIRST:
                return FIRST;
            case SECOND:
                return FIRST;
            case THIRD:
                return SECOND;
            case FOURTH:
                return THIRD;
        }
        return FIRST;
    }

}
