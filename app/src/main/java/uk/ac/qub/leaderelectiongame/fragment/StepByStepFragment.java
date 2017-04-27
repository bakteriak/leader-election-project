package uk.ac.qub.leaderelectiongame.fragment;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uk.ac.qub.leaderelectiongame.R;
import uk.ac.qub.leaderelectiongame.algorithm.LeaderElectionAlgorithm;
import uk.ac.qub.leaderelectiongame.consts.Consts;
import uk.ac.qub.leaderelectiongame.enums.StepByStepStage;
import uk.ac.qub.leaderelectiongame.helpers.GlobalHelper;
import uk.ac.qub.leaderelectiongame.model.Node;


/**
 * Fragment responsible for performing step by step algorithm presentation.
 */
public class StepByStepFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "StepByStepFragment";

    /**
     * Node info popup window.
     */
    private PopupWindow popupWindow;

    /**
     * Algoritm nodes.
     */
    private HashMap<Node, TextView> nodes;

    /**
     * Step by step caption text.
     */
    private TextView txtStepByStepCaption;

    /**
     * Step by step info text.
     */
    private TextView txtStepByStepInfo;

    /**
     * Step by step algorithm info text.
     */
    private TextView txtStepByStepAlgorithmInfo;

    /**
     * Button back.
     */
    private Button btnPrev;

    /**
     * Button next.
     */
    private Button btnNext;

    /**
     * Relative layout containing network nodes.
     */
    private RelativeLayout rlNetwork;

    /**
     * Node id text in popup.
     */
    private TextView txtPopupNodeId;

    /**
     * Node is participant text in popup.
     */
    private TextView txtPopupNodeParticipant;

    /**
     * Node referee is selected by text in popup.
     */
    private TextView txtPopupNodeCandidatesToReferee;

    /**
     * Node is referee text in popup.
     */
    private TextView txtPopupNodeReferee;

    /**
     * Node is winner text in popup.
     */
    private TextView txtPopupNodeWinner;

    /**
     * Current step by step stage.
     */
    private StepByStepStage currentStep;

    /**
     * Max step by step stage achieved by user.
     */
    private int stepAchieved;

    /**
     * Required empty public constructor.
     */
    public StepByStepFragment() {
    }

    /**
     * Method to create and return the view hierarchy associated with the fragment.
     */
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_step_by_step, container, false);

        //Initialising elements
        this.rlNetwork = (RelativeLayout) v.findViewById(R.id.rlNetwork);
        this.txtStepByStepCaption = (TextView) v.findViewById(R.id.txtStepByStepCaption);
        this.txtStepByStepInfo = (TextView) v.findViewById(R.id.txtStepByStepInfo);
        this.txtStepByStepAlgorithmInfo = (TextView) v.findViewById(R.id.txtStepByStepAlgorithmInfo);
        this.btnPrev = (Button) v.findViewById(R.id.btnPrev);
        this.btnNext = (Button) v.findViewById(R.id.btnNext);

        //Initialising nodes - first step
        this.changeStep(StepByStepStage.FIRST);

        //Handling buttons
        this.handleBtnNextClick();
        this.handleBtnPrevClick();
        this.handleNodesClick();
        return v;
    }

    /**
     * Method to initialize network from algorithm and display first step.
     * @return
     */
    private boolean initNodes() {
        try {
            List<Node> nodesFromAlgorithm = LeaderElectionAlgorithm.initNodes(Consts.PARTICIPANTS_NUMBER, getActivity());
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
            if (this.nodes == null) {
                this.nodes = new HashMap<>();
            }   //if
            Log.e(TAG, Log.getStackTraceString(ex));
            return false;
        }   //if
    }

    /**
     * Method to display first step from saved network state.
     * @return
     */
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
            Log.e(TAG, Log.getStackTraceString(ex));
            return false;
        }   //if
    }

    /**
     * Method to initialize participants from algorithm and display second step.
     * @return
     */
    private boolean initParticipants() {
        try {
            if (this.nodes == null) {
                return false;
            }   //if
            List<Node> participants = LeaderElectionAlgorithm.getParticipants(new ArrayList<>(this.nodes.keySet()));
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
            Log.e(TAG, Log.getStackTraceString(ex));
            return false;
        }   //if
    }

    /**
     * Method to display second step from saved network state.
     * @return
     */
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
            Log.e(TAG, Log.getStackTraceString(ex));
            return false;
        }   //if
    }

    /**
     * Method to initialize referees from algorithm and display third step.
     * @return
     */
    private boolean initReferees() {
        try {
            if (this.nodes == null) {
                return false;
            }   //if
            List<Node> refereesFromAlgorithm = LeaderElectionAlgorithm.getReferees(new ArrayList<>(this.nodes.keySet()));
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
            Log.e(TAG, Log.getStackTraceString(ex));
            return false;
        }   //if
    }

    /**
     * Method to display third step from saved network state.
     * @return
     */
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
            Log.e(TAG, Log.getStackTraceString(ex));
            return false;
        }   //if
    }

    /**
     * Method to initialize winner from algorithm and display fourth step.
     * @return
     */
    private boolean initWinners() {
        try {
            if (this.nodes == null) {
                return false;
            }   //if
            List<Node> winnersFromAlgorithm = LeaderElectionAlgorithm.findWinner(new ArrayList<>(this.nodes.keySet()), getActivity());
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
            Log.e(TAG, Log.getStackTraceString(ex));
            return false;
        }   //if
    }

    /**
     * Method to display fourth step from saved network state.
     * @return
     */
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
            Log.e(TAG, Log.getStackTraceString(ex));
            return false;
        }   //if
    }

    @Override
    public void onClick(View view) {

    }

    /**
     * Method to handle next step button click.
     */
    private void handleBtnNextClick() {
        btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                changeStep(currentStep.getNextStage());
            }
        });
    }

    /**
     * Method to handle previous step button click.
     */
    private void handleBtnPrevClick() {
        btnPrev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                changeStep(currentStep.getPrevStage());
            }
        });
    }

    /**
     * Method to change step (forward or backward).
     */
    private void changeStep(StepByStepStage newStep) {
        btnPrev.setText(getString(R.string.button_previous_step_by_step));
        btnNext.setText(getString(R.string.button_next_step_by_step));
        switch (newStep) {
            case FIRST:
                btnPrev.setText(getString(R.string.button_reset_step_by_step));
                if ((nodesFromAlgorithm(newStep) || (resetAlgorithm(newStep)))) {
                    initNodes();
                    this.stepAchieved = -1;
                } else {
                    showNodesFirstStep();
                }   //else
                if (getActivity() != null) {
                    txtStepByStepAlgorithmInfo.setText(String.format(getActivity().getString(R.string.step_by_step_stage_one_algorithm_info),
                            this.nodes.size(),
                            LeaderElectionAlgorithm.calculateMaxId(this.nodes.size())));
                }
                break;
            case SECOND:
                if (nodesFromAlgorithm(newStep)) {
                    initParticipants();
                } else {
                    showNodesSecondStep();
                }   //else
                if (getActivity() != null) {
                    txtStepByStepAlgorithmInfo.setText(String.format(getActivity().getString(R.string.step_by_step_stage_two_algorithm_info),
                            LeaderElectionAlgorithm.calculateParticipantProbability(this.nodes.size())));
                }
                break;
            case THIRD:
                if (nodesFromAlgorithm(newStep)) {
                    initReferees();
                } else {
                    showNodesThirdStep();
                }   //else
                if (getActivity() != null) {
                    txtStepByStepAlgorithmInfo.setText(String.format(getActivity().getString(R.string.step_by_step_stage_three_algorithm_info),
                            LeaderElectionAlgorithm.calculateRefereesAmount(this.nodes.size())));
                }   //if
                break;
            case FOURTH:
                btnNext.setText(getString(R.string.button_start_over_step_by_step));
                if (nodesFromAlgorithm(newStep)) {
                    initWinners();
                } else if (!startOverAlgorithm(newStep)) {
                    showNodesFourthStep();
                } else {  //else
                    this.stepAchieved = -1;
                    changeStep(StepByStepStage.FIRST);
                    return;
                }
                if (getActivity() != null) {
                    txtStepByStepAlgorithmInfo.setText(String.format(getActivity().getString(R.string.step_by_step_stage_four_algorithm_info),
                            LeaderElectionAlgorithm.findWinnerIdText(new ArrayList<>(this.nodes.keySet()))));
                }   //if
                break;
        }
        this.currentStep = newStep;
        if (getActivity() != null) {
            txtStepByStepCaption.setText(getActivity().getString(this.currentStep.getCaptionId()));
            txtStepByStepInfo.setText(getActivity().getString(this.currentStep.getInfoId()));
        }   //if
        if (this.stepAchieved < newStep.getNumber()) {
            this.stepAchieved = newStep.getNumber();
        }
    }

    /**
     * Method to decide whether nodes need to be initialized from algorithm or from saved network state.
     * @param newStep
     * @return
     */
    private boolean nodesFromAlgorithm(StepByStepStage newStep) {
        return (this.stepAchieved < newStep.getNumber());
    }

    /**
     * Method to determine if algorithm reset needed.
     * @param newStep
     * @return
     */
    private boolean resetAlgorithm(StepByStepStage newStep) {
        if (this.currentStep.getNumber() == 1) {
            return (newStep.getNumber() == this.currentStep.getNumber());
        }
        return false;
    }

    /**
     * Method to determine if algorithm start over needed.
     * @param newStep
     * @return
     */
    private boolean startOverAlgorithm(StepByStepStage newStep) {
        if (this.currentStep.getNumber() == 4) {
            return (newStep.getNumber() == this.currentStep.getNumber());
        }
        return false;
    }

    /**
     * Method to show popup window on node click.
     * @param textView
     */
    private void handleNodeClick(final TextView textView) {
        if (textView == null) {
            return;
        }   //if
        if (getActivity() == null) {
            return;
        }   //if
        textView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Node node = findNodeById(Integer.parseInt(textView.getText().toString()));
                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.popup_node, null);
                View baseView = layoutInflater.inflate(R.layout.activity_main, null);
                fillPopupData(node, popupView);
                if (node != null) {
                    popupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                    popupWindow.setBackgroundDrawable(new ColorDrawable());
                    popupWindow.setOutsideTouchable(true);
                    popupWindow.showAtLocation(baseView, Gravity.CENTER, 0, 0);
                }   //if
            }
        });
    }

    /**
     * Method to find node by id.
     * @param nodeId
     * @return
     */
    private Node findNodeById(int nodeId) {
        if (this.nodes == null) {
            return null;
        }   //if
        for (Node node: this.nodes.keySet()) {
            if (node.getId() == nodeId) {
                return node;
            }   //if
        }   //for
        return null;
    }

    /**
     * Method to handle nodes click.
     */
    private void handleNodesClick() {
        for (int i = 0; i < rlNetwork.getChildCount(); i++) {
            View v = rlNetwork.getChildAt(i);
            if (v instanceof TextView) {
                handleNodeClick((TextView) v);
            }   //if
        }   //for
    }

    /**
     * Method to fill popup window with node data.
     * @param node
     * @param popupView
     */
    private void fillPopupData(Node node, View popupView) {
        if (popupView == null) {
            return;
        }   //if
        initPopupElements(popupView);
        if (node == null) {
            clearPopupData(popupView);
            return;
        }   //if
        txtPopupNodeId.setText(String.valueOf(node.getId()));
        txtPopupNodeParticipant.setText(GlobalHelper.boolToString(node.isTakingPart()));
        txtPopupNodeReferee.setText(GlobalHelper.boolToString(node.isReferee()));
        txtPopupNodeWinner.setText(GlobalHelper.boolToString(node.isWinner()));
        if (node.isReferee()) {
            txtPopupNodeCandidatesToReferee.setText(GlobalHelper.flatIdsFromNodeList(node.getRefereeElectedBy()));
        } else {
            txtPopupNodeCandidatesToReferee.setText(Consts.BLANK_STRING_VALUE);
        }   //else
    }

    /**
     * Method to clear popup window node data.
     * @param popupView
     */
    private void clearPopupData(View popupView) {
        if (popupView == null) {
            return;
        }   //if
        initPopupElements(popupView);
        txtPopupNodeCandidatesToReferee.setText(Consts.BLANK_STRING_VALUE);
        txtPopupNodeId.setText(Consts.BLANK_STRING_VALUE);
        txtPopupNodeParticipant.setText(Consts.BLANK_STRING_VALUE);
        txtPopupNodeReferee.setText(Consts.BLANK_STRING_VALUE);
        txtPopupNodeWinner.setText(Consts.BLANK_STRING_VALUE);
    }

    /**
     * Method to init popup window elements.
     * @param popupView
     */
    private void initPopupElements(View popupView) {
        if (popupView == null) {
            return;
        }   //if
        this.txtPopupNodeId = (TextView) popupView.findViewById(R.id.txtPopupNodeId);
        this.txtPopupNodeCandidatesToReferee = (TextView) popupView.findViewById(R.id.txtPopupNodeCandidatesToReferee);
        this.txtPopupNodeWinner = (TextView) popupView.findViewById(R.id.txtPopupNodeWinner);
        this.txtPopupNodeParticipant = (TextView) popupView.findViewById(R.id.txtPopupNodeParticipant);
        this.txtPopupNodeReferee = (TextView) popupView.findViewById(R.id.txtPopupNodeReferee);
    }

}
