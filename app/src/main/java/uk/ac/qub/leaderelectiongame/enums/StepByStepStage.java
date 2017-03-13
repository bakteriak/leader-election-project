package uk.ac.qub.leaderelectiongame.enums;


import uk.ac.qub.leaderelectiongame.consts.Consts;

public enum StepByStepStage {

    FIRST(1,
            Consts.STEP_BY_STEP_STAGE_ONE_CAPTION,
            Consts.STEP_BY_STEP_STAGE_ONE_EXPLANATION,
            Consts.STEP_BY_STEP_STAGE_ONE_ALGORITHM_INFO),
    SECOND(2,
            Consts.STEP_BY_STEP_STAGE_TWO_CAPTION,
            Consts.STEP_BY_STEP_STAGE_TWO_EXPLANATION,
            Consts.STEP_BY_STEP_STAGE_TWO_ALGORITHM_INFO),
    THIRD(3,
            Consts.STEP_BY_STEP_STAGE_THREE_CAPTION,
            Consts.STEP_BY_STEP_STAGE_THREE_EXPLANATION,
            Consts.STEP_BY_STEP_STAGE_THREE_ALGORITHM_INFO),
    FOURTH(4,
            Consts.STEP_BY_STEP_STAGE_FOUR_CAPTION,
            Consts.STEP_BY_STEP_STAGE_FOUR_EXPLANATION,
            Consts.STEP_BY_STEP_STAGE_FOUR_ALGORITHM_INFO);

    private final String caption;
    private final String info;
    private final String algorithmInfo;
    private final int number;

    private StepByStepStage(int number, String caption, String info, String algorithmInfo) {
        this.number = number;
        this.caption = caption;
        this.info = info;
        this.algorithmInfo = algorithmInfo;
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

    public String getaAlgorithmInfo() {
        return algorithmInfo;
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
