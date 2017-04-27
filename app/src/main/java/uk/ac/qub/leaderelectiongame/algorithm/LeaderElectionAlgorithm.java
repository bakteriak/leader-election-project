package uk.ac.qub.leaderelectiongame.algorithm;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import uk.ac.qub.leaderelectiongame.R;
import uk.ac.qub.leaderelectiongame.consts.Consts;
import uk.ac.qub.leaderelectiongame.exceptions.LeaderElectionException;
import uk.ac.qub.leaderelectiongame.model.Node;

/**
 * Class responsible for leader election algorithm calculations.
 */
public class LeaderElectionAlgorithm {

    /**
     * Random object.
     */
    private static final Random random = new Random(System.currentTimeMillis());

    /**
     * Number of algorithm participants.
     */
    private static int candidatesNumber = 0;

    /**
     * Number of algorithm winners.
     */
    private static int winnersNumber = 0;

    /**
     * Algorithm referees array.
     */
    private static List<Node> referees = new ArrayList<>();

    /**
     * Algorithm participants array.
     */
    private static List<Node> participants = new ArrayList<>();

    /**
     * Method to initialize algorithm network - nodes with id.
     * @param participantsNumber
     * @param context
     * @return list of inited nodes
     * @throws LeaderElectionException
     */
    public static List<Node> initNodes(int participantsNumber, Context context) throws LeaderElectionException {
        if (context == null) {
            return new ArrayList<>();
        }   //if
        List<Node> result = new ArrayList<>();
        LeaderElectionAlgorithm.candidatesNumber = 0;
        LeaderElectionAlgorithm.winnersNumber = 0;
        LeaderElectionAlgorithm.referees = new ArrayList<>();
        LeaderElectionAlgorithm.participants = new ArrayList<>();
        if (participantsNumber <= 0) {
            return  result;
        }   //if
        int maxId = calculateMaxId(participantsNumber);
        if (participantsNumber > maxId) {
            throw new LeaderElectionException(context.getResources().getString(R.string.algorithm_error_too_many_participants));
        }   //if
        Set<Integer> assignedIds = new HashSet<>();
        for (int i = 0; i < participantsNumber; i++) {
            int newId = random.nextInt(maxId) + 1;
            while (true) {
                if (!assignedIds.contains(newId)) {
                    break;
                }
                newId = random.nextInt(maxId) + 1;
            }
            assignedIds.add(newId);
            result.add(new Node(newId));
        }   //for
        return result;
    }

    /**
     * Method to initialize algorithm network with setting neighbours.
     * @param participantsNumber
     * @param context
     * @return list of inited nodes, with neighbours set
     * @throws LeaderElectionException
     */
    public static List<Node> initNodesWithNeighbours(int participantsNumber, Context context) throws LeaderElectionException {
        if (context == null) {
            return new ArrayList<>();
        }   //if
        List<Node> result = new ArrayList<>();
        LeaderElectionAlgorithm.candidatesNumber = 0;
        LeaderElectionAlgorithm.winnersNumber = 0;
        LeaderElectionAlgorithm.referees = new ArrayList<>();
        LeaderElectionAlgorithm.participants = new ArrayList<>();
        if (participantsNumber <= 0) {
            return  result;
        }   //if
        int maxId = calculateMaxId(participantsNumber);
        if (participantsNumber > maxId) {
            throw new LeaderElectionException(context.getResources().getString(R.string.algorithm_error_too_many_participants));
        }   //if
        Set<Integer> assignedIds = new HashSet<>();
        for (int i = 0; i < participantsNumber; i++) {
            int newId = random.nextInt(maxId) + 1;
            while (true) {
                if (!assignedIds.contains(newId)) {
                    break;
                }
                newId = random.nextInt(maxId) + 1;
            }
            assignedIds.add(newId);
            result.add(new Node(newId));
        }   //for
        for (Node firstNode: result) {
            for (Node secondNode: result) {
                if (firstNode != secondNode) {
                    connect(firstNode, secondNode);
                }   //if
            }   //for
        }   //for
        return result;
    }

    /**
     * Method to set first and second node as neighbours.
     * @param firstNode
     * @param secondNode
     */
    private static void connect(Node firstNode, Node secondNode) {
        if (firstNode == null) {
            return;
        }   //if
        if (secondNode == null) {
            return;
        }   //if
        if (firstNode.getNeighbours() == null) {
            firstNode.addNeighbour(secondNode);
        } else if (!firstNode.getNeighbours().contains(secondNode)) {
            firstNode.addNeighbour(secondNode);
        }   //if
        if (secondNode.getNeighbours() == null) {
            secondNode.addNeighbour(firstNode);
        } else if (!secondNode.getNeighbours().contains(firstNode)) {
            secondNode.addNeighbour(firstNode);
        }   //if
    }


    /**
     * Method to set algorithm participants.
     * @param allNodes
     * @return list of nodes with participants set
     */
    public static List<Node> getParticipants(List<Node> allNodes) {
        if (allNodes == null) {
            return new ArrayList<>();
        }   //if
        if (allNodes.isEmpty()) {
            return new ArrayList<>();
        }   //if
        boolean atLeastOneParticipant = false;
        List<Node> result = new ArrayList<>();
        while (!atLeastOneParticipant) {
            LeaderElectionAlgorithm.participants.clear();
            result.clear();
            for (int i = 0; i < allNodes.size(); i++) {
                Node node = allNodes.get(i);
                ///prob 2 log n/n = 2 for 10 contenders
                node.setTakingPart(random.nextDouble() <= calculateParticipantProbability(allNodes.size()));
                if (node.isTakingPart()) {
                    atLeastOneParticipant = true;
                    LeaderElectionAlgorithm.participants.add(node);
                }   //if
                result.add(node);
            }
        }   //while
        LeaderElectionAlgorithm.candidatesNumber = participants.size();
        return result;
    }

    /**
     * Method to check if chosen node can be a referee.
     * @param samplingNode
     * @param eventualReferee
     * @return
     */
    private static boolean checkCanBeReferee(Node samplingNode, Node eventualReferee) {
        if ((samplingNode == null) || (eventualReferee == null)) {
            return false;
        }   //if
        //you cannot choose yourself
        if (samplingNode.getId() == eventualReferee.getId()) {
            return false;
        }   //if
        //it is not referee at all, can be chosen
        if (!eventualReferee.isReferee()) {
            return true;
        }   //if
        //check if it was already chosen by sampling node
        return (!eventualReferee.getRefereeElectedBy().contains(samplingNode));
    }

    /**
     * Method to choose referee by a single node.
     * @param samplingNode
     * @param allNodes
     * @param refereesAmount
     * @return
     */
    private static List<Node> sampleRefereeForSingleNode(Node samplingNode, List<Node> allNodes, int refereesAmount) {
        if (allNodes == null) {
            return new ArrayList<>();
        }   //if
        if (allNodes.isEmpty()) {
            return new ArrayList<>();
        }   //if
        if (samplingNode == null) {
            return allNodes;
        }   //if
        if ((refereesAmount <= 0) || (allNodes.size() < (refereesAmount + 1))) {
            return allNodes;
        }   //if
        if (!samplingNode.isTakingPart()) {
            return allNodes;
        }   //if
        List<Integer> exclusions = new ArrayList<>();
        exclusions.add(allNodes.indexOf(samplingNode)); //you cannot choose yourself
        for (int i = 0; i < refereesAmount; i++) {
            //sampling referee/non-referee
            int randomNodeIndex = getRandomWithExclusion(allNodes.size(), exclusions);
            while (true) {
                if (checkCanBeReferee(samplingNode, allNodes.get(randomNodeIndex))) {
                    allNodes.get(randomNodeIndex).setReferee(true);
                    allNodes.get(randomNodeIndex).addRefereeElector(samplingNode);
                    if (!LeaderElectionAlgorithm.referees.contains(allNodes.get(randomNodeIndex))) {
                        LeaderElectionAlgorithm.referees.add(allNodes.get(randomNodeIndex));
                    }   //if
                    exclusions.add(randomNodeIndex);
                    break;
                }  //if
                randomNodeIndex = getRandomWithExclusion(allNodes.size(), exclusions);
            }   //while
        }   //for
        return allNodes;
    }

    /**
     * Method to get random number, excluding given numbers (exclusions).
     * @param range
     * @param exclusions
     * @return
     */
    private static int getRandomWithExclusion(int range, List<Integer> exclusions) {
        if (exclusions == null) {
            exclusions = new ArrayList<>();
        }   //if
        int randomIndex = random.nextInt(range);
        while(exclusions.contains(randomIndex)) {
            randomIndex = random.nextInt(range);
        }   //while
        return randomIndex;
    }

    /**
     * Method to choose algorithm referees in network.
     * @param allNodes
     * @return list of nodes with referees set
     */
    public static List<Node> getReferees(List<Node> allNodes) {
        if ((allNodes == null) || (LeaderElectionAlgorithm.participants == null)) {
            return new ArrayList<>();
        }   //if
        if (allNodes.isEmpty()) {
            return new ArrayList<>();
        }   //if
        List<Node> result = allNodes;
        int refereesAmount = calculateRefereesAmount(allNodes.size());
        for (Node node: LeaderElectionAlgorithm.participants) {
            result = sampleRefereeForSingleNode(node, result, refereesAmount);
        }   //for
        return result;
    }

    /**
     * Method in which single referee is nominating winner.
     * @param currentReferee
     * @param allNodes
     * @param context
     * @return
     * @throws LeaderElectionException
     */
    private static List<Node> nominateWinner(Node currentReferee, List<Node> allNodes, Context context) throws LeaderElectionException {
        if (context == null) {
            return new ArrayList<>();
        }   //if
        if (allNodes == null) {
            return new ArrayList<>();
        }   //if
        if (allNodes.isEmpty()) {
            return new ArrayList<>();
        }   //if
        if (currentReferee == null) {
            return allNodes;
        }   //if
        if (!currentReferee.isReferee()) {
            return allNodes;
        }   //if
        int highestId = -1;
        int highestIdIndex = -1;
        ///finding highest id
        for (Node candidate: currentReferee.getRefereeElectedBy()) {
            if (candidate.getId() > highestId) {
                highestId = candidate.getId();
                highestIdIndex = allNodes.indexOf(candidate);
            }   //if
        }   //for
        ///nominating highest id
        if (highestIdIndex >= 0) {
            allNodes.get(highestIdIndex).incNumberOfNominations();
        } else {    //if
            throw new LeaderElectionException(context.getResources().getString(R.string.algorithm_error_referee_cannot_nominate_winner));
        }   //else
        return allNodes;
    }

    /**
     * Method to choose winner from all participants.
     * @param allNodes
     * @param context
     * @return list of nodes with winners set
     * @throws LeaderElectionException
     */
    public static List<Node> findWinner(List<Node> allNodes, Context context) throws LeaderElectionException {
        if (context == null) {
            return new ArrayList<>();
        }   //if
        if ((allNodes == null) || (LeaderElectionAlgorithm.referees == null) || (LeaderElectionAlgorithm.participants == null)) {
            return new ArrayList<>();
        }   //if
        if (allNodes.isEmpty()) {
            return new ArrayList<>();
        }   //if
        List<Node> result = allNodes;
        ///nominating winners
        for (Node node: LeaderElectionAlgorithm.referees) {
            result = nominateWinner(node, result, context);
        }   //for
        int winnerIndex = -1;
        int highestNominationNum = -1;
        List<Node> winners = new ArrayList<>();
        ///find winner
        for (Node node: LeaderElectionAlgorithm.participants) {
            if (node.getNumberOfNominations() > highestNominationNum) {
                winners.clear();
                winners.add(node);
                highestNominationNum = node.getNumberOfNominations();
                winnerIndex = allNodes.indexOf(node);
            } else if (node.getNumberOfNominations() == highestNominationNum) {  //if
                winners.add(node);
            }   //else
        }   //for
        ///setting winner
        if (winnerIndex >= 0) {
            allNodes.get(winnerIndex).setWinner(true);
        } else {    //if
            throw new LeaderElectionException(context.getResources().getString(R.string.algorithm_error_referees_cannot_nominate_winner));
        }   //else
        LeaderElectionAlgorithm.winnersNumber = winners.size();
        return allNodes;
    }

    /**
     * Method calculating referees number = ceil (sqrt (n log n)).
     * @param networkSize
     * @return referees number
     */
    public static int calculateRefereesAmount(int networkSize) {
        if (networkSize <= 0) {
            return 0;
        }
        return 2 * ((int) Math.ceil(Math.sqrt((double) networkSize)));
    }

    /**
     * Method calculating probability of becoming participant = 2 log n/n.
     * @param networkSize
     * @return becoming participant probability
     */
    public static double calculateParticipantProbability(int networkSize) {
        if (networkSize <= 0) {
            return 0;
        }
        return ((2 * Math.log10(networkSize)) / networkSize);
    }

    /**
     * Method calculating max range of nodes id = n^4.
     * @param networkSize
     * @return max range od nodes id
     */
    public static int calculateMaxId(int networkSize) {
        if (networkSize <= 0) {
            return 0;
        }
        return (int)Math.pow(networkSize, Consts.MAX_ID_POWER);
    }

    /**
     * Method to get winners id.
     * @param allNodes
     * @return winner id
     */
    public static String findWinnerIdText(List<Node> allNodes) {
        if (allNodes == null) {
            return "--";
        }   //if
        if (allNodes.size() == 0) {
            return "--";
        }   //if
        for (Node node: allNodes) {
            if (node.isWinner()) {
                return String.valueOf(node.getId());
            }
        }   //for
        return "--";
    }

    /**
     * Candidates number getter.
     * @return candidates number
     */
    public static int getCandidatesNumber() {
        return candidatesNumber;
    }

    /**
     * Winnners number getter.
     * @return winners number
     */
    public static int getWinnersNumber() {
        return winnersNumber;
    }

    /**
     * Method to calculate number of edges for full graph.
     * @param networkSize
     * @return numbe rof edges for full graph
     */
    public static int getNumberOfEdges(int networkSize) {
        if (networkSize <= 0) {
            return 0;
        }   //if
        return ((networkSize * (networkSize - 1)) / 2);
    }

}
