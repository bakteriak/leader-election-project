package uk.ac.qub.leaderelectiongame.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import uk.ac.qub.leaderelectiongame.R;
import uk.ac.qub.leaderelectiongame.algorithm.LeaderElectionAlgorithm;
import uk.ac.qub.leaderelectiongame.consts.Consts;
import uk.ac.qub.leaderelectiongame.enums.StepByStepStage;
import uk.ac.qub.leaderelectiongame.exceptions.LeaderElectionException;
import uk.ac.qub.leaderelectiongame.model.Node;


/**
 * A simple {@link Fragment} subclass.
 */
public class StepByStepFragment extends Fragment implements View.OnClickListener {

    //layout elements
    private HashMap<Node, TextView> nodes;
    private TextView txtStepByStepCaption;
    private TextView txtStepByStepInfo;
    private Button btnPrev;
    private Button btnNext;
    private RelativeLayout rlNetwork;

    private StepByStepStage currentStep;
    private int stepAchieved;

    // Required empty public constructor
    public StepByStepFragment() {
    }

    protected void onCreate() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_step_by_step, container, false);

        //Initialising elements
        this.rlNetwork = (RelativeLayout) v.findViewById(R.id.rlNetwork);
        this.txtStepByStepCaption = (TextView) v.findViewById(R.id.txtStepByStepCaption);
        this.txtStepByStepInfo = (TextView) v.findViewById(R.id.txtStepByStepInfo);
        this.btnPrev = (Button) v.findViewById(R.id.btnPrev);
        this.btnNext = (Button) v.findViewById(R.id.btnNext);

        //Initialising nodes - first step
        this.changeStep(StepByStepStage.FIRST);

        //Handling buttons
        this.handleBtnNextClick();
        this.handleBtnPrevClick();
        return v;
    }

    ///initializng network from algorithm and display first step
    private boolean initNodes() {
        try {
            List<Node> nodesFromAlgorithm = LeaderElectionAlgorithm.initNodes(Consts.PARTICIPANTS_NUMBER);
            this.nodes = new HashMap<>();
            for (int i = 0; i < rlNetwork.getChildCount(); i++) {
                View v = rlNetwork.getChildAt(i);
                if (v instanceof TextView) {
                    if (nodesFromAlgorithm.size() > i) {
                        this.nodes.put(nodesFromAlgorithm.get(i), (TextView) v);
                        ((TextView) v).setText(String.valueOf(nodesFromAlgorithm.get(i).getId()));
                        if (getActivity() != null) {
                            v.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.node_bg));
                            ((TextView) v).setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.nodeText));
                        }   //if
                    }   //if
                }   //if
            }   //for
            return true;
        } catch (Exception ex) {
            return false;
        }   //if
    }

    ///only display change
    private boolean showNodesFirstStep() {
        try {
            if (this.nodes == null) {
                return false;
            }   //if
            List<Node> nodes = new ArrayList<>(this.nodes.keySet());
            for (Node node: nodes) {
                if (this.nodes.containsKey(node)) {
                    if (getActivity() != null) {
                        TextView v = this.nodes.get(node);
                        v.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.node_bg));
                        v.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.nodeText));
                    }   //if
                }   //if
            }   //for
            return true;
        } catch (Exception ex) {
            return false;
        }   //if
    }

    ///initializng participants from algorithm and display second step
    private boolean initParticipants() {
        try {
            if (this.nodes == null) {
                return false;
            }   //if
            List<Node> participants = LeaderElectionAlgorithm.getParticipants(new ArrayList<Node>(this.nodes.keySet()));
            for (Node participant: participants) {
                if (participant.isTakingPart()) {
                    if (this.nodes.containsKey(participant)) {
                        if (getActivity() != null) {
                            TextView participantTextView = this.nodes.get(participant);
                            participantTextView.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.participant_bg));
                            participantTextView.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.white));
                        }   //if
                    }   //if
                }   //if
            }   //for
            return true;
        } catch (Exception ex) {
            return false;
        }   //if
    }

    ///only display change
    private boolean showNodesSecondStep() {
        try {
            if (this.nodes == null) {
                return false;
            }   //if
            List<Node> nodes = new ArrayList<>(this.nodes.keySet());
            for (Node participant: nodes) {
                if (this.nodes.containsKey(participant)) {
                    if (getActivity() != null) {
                        TextView v = this.nodes.get(participant);
                        if (participant.isTakingPart()) {
                            v.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.participant_bg));
                            v.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.white));
                        } else {   //if
                            v.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.node_bg));
                            v.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.nodeText));
                        }   //if
                    }   //else
                }   //if
            }   //for
            return true;
        } catch (Exception ex) {
            return false;
        }   //if
    }

    ///initializng referees from algorithm and display third step
    private boolean initReferees() {
        try {
            if (this.nodes == null) {
                return false;
            }   //if
            List<Node> refereesFromAlgorithm = LeaderElectionAlgorithm.getReferees(new ArrayList<Node>(this.nodes.keySet()));
            for (Node referee: refereesFromAlgorithm) {
                if (referee.isReferee()) {
                    if (this.nodes.containsKey(referee)) {
                        if (getActivity() != null) {
                            TextView refereeTextView = this.nodes.get(referee);
                            if (referee.isTakingPart()) {
                                refereeTextView.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.referee_participant_bg));
                            } else {
                                refereeTextView.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.referee_bg));
                            }
                        }   //if
                    }   //if
                }   //if
            }   //for
            return true;
        } catch (Exception ex) {
            return false;
        }   //if
    }

    ///only display change
    private boolean showNodesThirdStep() {
        try {
            if (this.nodes == null) {
                return false;
            }   //if
            List<Node> nodes = new ArrayList<>(this.nodes.keySet());
            for (Node node: nodes) {
                if (this.nodes.containsKey(node)) {
                    if (getActivity() != null) {
                        TextView v = this.nodes.get(node);
                        if (node.isReferee()) {
                            if (node.isTakingPart()) {
                                v.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.referee_participant_bg));
                            } else {
                                v.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.referee_bg));
                            }
                        } else {
                            if (node.isTakingPart()) {
                                v.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.participant_bg));
                                v.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.white));
                            } else {   //if
                                v.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.node_bg));
                                v.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.nodeText));
                            }   //if
                        }   //else referee
                    }   //else
                }   //if
            }   //for
            return true;
        } catch (Exception ex) {
            return false;
        }   //if
    }

    ///initializng winner from algorithm and display fourth step
    private boolean initWinners() {
        try {
            if (this.nodes == null) {
                return false;
            }   //if
            List<Node> winnersFromAlgorithm = LeaderElectionAlgorithm.findWinner(new ArrayList<Node>(this.nodes.keySet()));
            for (Node winner: winnersFromAlgorithm) {
                if (winner.isWinner()) {
                    if (this.nodes.containsKey(winner)) {
                        if (getActivity() != null) {
                            TextView refereeTextView = this.nodes.get(winner);
                            refereeTextView.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.winner_bg));
                        }   //if
                    }   //if
                }   //if
            }   //for
            return true;
        } catch (Exception ex) {
            return false;
        }   //if
    }

    ///only display change
    private boolean showNodesFourthStep() {
        try {
            if (this.nodes == null) {
                return false;
            }   //if
            List<Node> nodes = new ArrayList<>(this.nodes.keySet());
            for (Node node: nodes) {
                if (this.nodes.containsKey(node)) {
                    if (getActivity() != null) {
                        TextView v = this.nodes.get(node);
                        if (node.isWinner()) {
                            v.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.winner_bg));
                        } else {
                            if (node.isReferee()) {
                                if (node.isTakingPart()) {
                                    v.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.referee_participant_bg));
                                } else {
                                    v.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.referee_bg));
                                }
                            } else {
                                if (node.isTakingPart()) {
                                    v.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.participant_bg));
                                    v.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.white));
                                } else {   //if
                                    v.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.node_bg));
                                    v.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.nodeText));
                                }   //if
                            }   //else referee
                        }   //else winner
                    }   //else
                }   //if
            }   //for
            return true;
        } catch (Exception ex) {
            return false;
        }   //if
    }

    @Override
    public void onClick(View view) {

    }

    private void handleBtnNextClick() {
        btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                changeStep(currentStep.getNextStage());
            }
        });
    }

    private void handleBtnPrevClick() {
        btnPrev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                changeStep(currentStep.getPrevStage());
            }
        });
    }

    private void changeStep(StepByStepStage newStep) {
        btnPrev.setText(getString(R.string.button_previous_step_by_step));
        btnNext.setVisibility(View.VISIBLE);
        switch (newStep) {
            case FIRST:
                btnPrev.setText(getString(R.string.button_reset_step_by_step));
                if ((nodesFromAlgorithm(newStep) || (resetAlgorithm(newStep)))) {
                    initNodes();
                    this.stepAchieved = -1;
                } else {
                    showNodesFirstStep();
                }   //else
                break;
            case SECOND:
                if (nodesFromAlgorithm(newStep)) {
                    initParticipants();
                } else {
                    showNodesSecondStep();
                }   //else
                break;
            case THIRD:
                if (nodesFromAlgorithm(newStep)) {
                    initReferees();
                } else {
                    showNodesThirdStep();
                }   //else
                break;
            case FOURTH:
                if (nodesFromAlgorithm(newStep)) {
                    initWinners();
                } else {
                    showNodesFourthStep();
                }   //else
                btnNext.setVisibility(View.INVISIBLE);
                break;
        }
        this.currentStep = newStep;
        txtStepByStepCaption.setText(this.currentStep.getCaption());
        txtStepByStepInfo.setText(this.currentStep.getInfo());
        if (this.stepAchieved < newStep.getNumber()) {
            this.stepAchieved = newStep.getNumber();
        }
    }

    ///decide whether nodes need to be initialized from algorithm or from memory
    private boolean nodesFromAlgorithm(StepByStepStage newStep) {
        return (this.stepAchieved < newStep.getNumber());
    }

    ///algorithm start over
    private boolean resetAlgorithm(StepByStepStage newStep) {
        if (this.currentStep.getNumber() == 1) {
            return (newStep.getNumber() == this.currentStep.getNumber());
        }
        return false;
    }

}
