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

    private static Random random = new Random(System.currentTimeMillis());

    private static int candidatesNumber = 0;
    private static int winnersNumber = 0;
    private static List<Node> referees = new ArrayList<>();
    private static List<Node> participants = new ArrayList<>();

    public static List<Node> initNodes(int participantsNumber) throws LeaderElectionException {
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
            throw new LeaderElectionException(Consts.ALGORITHM_ERROR_TOO_MANY_PARTICIPANTS);
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
        }
        return result;
    }

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

    public static List<Node> getReferees(List<Node> allNodes) throws LeaderElectionException {
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
        if ((allNodes == null) || (LeaderElectionAlgorithm.referees == null) || (LeaderElectionAlgorithm.participants == null)) {
            return new ArrayList<>();
        }   //if
        if (allNodes.isEmpty()) {
            return new ArrayList<>();
        }   //if
        List<Node> result = allNodes;
        ///nominating winners
        for (Node node: LeaderElectionAlgorithm.referees) {
            result = nominateWinner(node, result);
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
            throw new LeaderElectionException(Consts.ALGORITHM_ERROR_REFEREES_CANNOT_NOMINATE_WINNER);
        }   //else
        LeaderElectionAlgorithm.winnersNumber = winners.size();
        return allNodes;
    }

    //each node chooses ceil (sqrt (n log n)) referees = 4 or 2 * ceil (sqrt (n log n)) referees = 8 referees
    public static int calculateRefereesAmount(int networkSize) {
        if (networkSize <= 0) {
            return 0;
        }
        return 2 * ((int) Math.ceil(Math.sqrt((double) networkSize)));
    }

    //probability of becoming participant - 2 log n/n
    public static double calculateParticipantProbability(int networkSize) {
        if (networkSize <= 0) {
            return 0;
        }
        return ((2 * Math.log10(networkSize)) / networkSize);
    }

    //max random id = n^4
    public static int calculateMaxId(int networkSize) {
        if (networkSize <= 0) {
            return 0;
        }
        return (int)Math.pow(networkSize, Consts.MAX_ID_POWER);
    }

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

    public static int getCandidatesNumber() {
        return candidatesNumber;
    }

    public static int getWinnersNumber() {
        return winnersNumber;
    }
}
