package uk.ac.qub.leaderelectiongame.algorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import uk.ac.qub.leaderelectiongame.consts.Consts;
import uk.ac.qub.leaderelectiongame.exceptions.LeaderElectionException;
import uk.ac.qub.leaderelectiongame.model.Node;

public class LeaderElectionAlgorithm {

    private static Random random = new Random();

    public static List<Node> initNodes(int participantsNumber) throws LeaderElectionException {
        List<Node> result = new ArrayList<>();
        if (participantsNumber <= 0) {
            return  result;
        }   //if
        if (participantsNumber > Consts.MAX_ID) {
            throw new LeaderElectionException(Consts.ALGORITHM_ERROR_TOO_MANY_PARTICIPANTS);
        }   //if
        Set<Integer> assignedIds = new HashSet<>();
        for (int i = 0; i < participantsNumber; i++) {
            int newId = random.nextInt(Consts.MAX_ID) + 1;
            while (true) {
                if (!assignedIds.contains(newId)) {
                    break;
                }
                newId = random.nextInt(Consts.MAX_ID) + 1;
            }
            assignedIds.add(newId);
            result.add(new Node(newId));
        }
        return result;
    }

    public static List<Node> getParticipants(List<Node> allNodes) {
        List<Node> result = new ArrayList<>();
        if (allNodes == null) {
            return result;
        }   //if
        if (allNodes.isEmpty()) {
            return result;
        }   //if
        for (int i = 0; i < allNodes.size(); i++) {
            Node node = allNodes.get(i);
            ///prob 2 log n/n = 2 for 10 contenders
            node.setTakingPart(((random.nextInt(Consts.MAX_ID) + 1) % 2) == 0);
            result.add(node);
        }
        return result;
    }

    //check if chosen node can be referee
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

    ///single node chooses referees
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
        for (int i = 0; i < refereesAmount; i++) {
            //sampling referee/non-referee
            int randomNodeIndex = random.nextInt(allNodes.size());
            while (true) {
                if (checkCanBeReferee(samplingNode, allNodes.get(randomNodeIndex))) {
                    allNodes.get(randomNodeIndex).setReferee(true);
                    allNodes.get(randomNodeIndex).addRefereeElector(samplingNode);
                    break;
                }   //if
                randomNodeIndex = random.nextInt(allNodes.size());
            }   //while
        }   //for
        return allNodes;
    }

    public static List<Node> getReferees(List<Node> allNodes) {
        if (allNodes == null) {
            return new ArrayList<>();
        }   //if
        if (allNodes.isEmpty()) {
            return new ArrayList<>();
        }   //if
        List<Node> result = allNodes;
        int refereesAmount = calculateRefereesAmount(allNodes.size());
        for (Node node: allNodes) {
            result = sampleRefereeForSingleNode(node, result, refereesAmount);
        }   //for
        return result;
    }

    ///single referee nominates winner
    private static List<Node> nominateWinner(Node currentReferee, List<Node> allNodes) throws LeaderElectionException {
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
            throw new LeaderElectionException(Consts.ALGORITHM_ERROR_REFEREE_CANNOT_NOMINATE_WINNER);
        }   //else
        return allNodes;
    }

    ///finding winner
    public static List<Node> findWinner(List<Node> allNodes) throws LeaderElectionException {
        if (allNodes == null) {
            return new ArrayList<>();
        }   //if
        if (allNodes.isEmpty()) {
            return new ArrayList<>();
        }   //if
        List<Node> result = allNodes;
        ///nominating winners
        for (Node node: allNodes) {
            result = nominateWinner(node, result);
        }   //for
        int winnerIndex = -1;
        int highestNominationNum = -1;
        ///find winner
        for (Node node: allNodes) {
            if (node.getNumberOfNominations() > highestNominationNum) {
                highestNominationNum = node.getNumberOfNominations();
                winnerIndex = allNodes.indexOf(node);
            }   //if
        }   //for
        ///setting winner
        if (winnerIndex >= 0) {
            allNodes.get(winnerIndex).setWinner(true);
        } else {    //if
            throw new LeaderElectionException(Consts.ALGORITHM_ERROR_REFEREES_CANNOT_NOMINATE_WINNER);
        }   //else
        return allNodes;
    }

    //each node chooses ceil (sqrt (n log n)) referees = 4 or 2 * ceil (sqrt (n log n)) referees = 8 referees
    private static int calculateRefereesAmount(int networkSize) {
        if (networkSize <= 0) {
            return 0;
        }
        return 2 * ((int) Math.ceil(Math.sqrt((double) networkSize)));
    }

}
