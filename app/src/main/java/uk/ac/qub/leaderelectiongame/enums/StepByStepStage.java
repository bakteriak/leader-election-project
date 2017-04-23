package uk.ac.qub.leaderelectiongame.enums;


import uk.ac.qub.leaderelectiongame.R;

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

    private int captionId;
    private int infoId;
    private final int number;

    StepByStepStage(int number, int captionId, int infoId) {
        this.number = number;
        this.captionId = captionId;
        this.infoId = infoId;
    }

    public int getCaptionId() {
        return captionId;
    }

    public int getInfoId() {
        return infoId;
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
