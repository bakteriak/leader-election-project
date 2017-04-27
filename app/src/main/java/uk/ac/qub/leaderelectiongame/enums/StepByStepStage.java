package uk.ac.qub.leaderelectiongame.enums;


import uk.ac.qub.leaderelectiongame.R;

/**
 * Step by step stages enum.
 */
public enum StepByStepStage {

    FIRST(1,
            R.string.step_by_step_stage_one_caption,
            R.string.step_by_step_stage_one_explanation),
    SECOND(2,
            R.string.step_by_step_stage_two_caption,
            R.string.step_by_step_stage_two_explanation),
    THIRD(3,
            R.string.step_by_step_stage_three_caption,
            R.string.step_by_step_stage_three_explanation),
    FOURTH(4,
            R.string.step_by_step_stage_four_caption,
            R.string.step_by_step_stage_four_explanation);

    /**
     * Caption string res id.
     */
    private int captionId;

    /**
     * Info string res id.
     */
    private int infoId;

    /**
     * Number of step.
     */
    private final int number;

    StepByStepStage(int number, int captionId, int infoId) {
        this.number = number;
        this.captionId = captionId;
        this.infoId = infoId;
    }


    /**
     * Caption string res id getter.
     */
    public int getCaptionId() {
        return captionId;
    }

    /**
     * Info string res id getter.
     */
    public int getInfoId() {
        return infoId;
    }

    /**
     * Step number getter.
     */
    public int getNumber() {
        return number;
    }

    /**
     * Method to calculate next step.
     */
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

    /**
     * Method to calculate previous step.
     */
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
