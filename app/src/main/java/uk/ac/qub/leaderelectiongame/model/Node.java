package uk.ac.qub.leaderelectiongame.model;

import java.util.ArrayList;
import java.util.List;

//Single node
public class Node {

    private int id;
    private boolean takingPart;
    private boolean referee;
    private boolean winner;
    private List<Node> refereeElectedBy;
    private int numberOfNominations;

    public Node(int id) {
        this.id = id;
        this.takingPart = false;
        this.referee = false;
        this.winner = false;
        this.refereeElectedBy = new ArrayList<>();
        this.numberOfNominations = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isTakingPart() {
        return takingPart;
    }

    public void setTakingPart(boolean takingPart) {
        this.takingPart = takingPart;
    }

    public boolean isReferee() {
        return referee;
    }

    public void setReferee(boolean referee) {
        this.referee = referee;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public List<Node> getRefereeElectedBy() {
        return refereeElectedBy;
    }

    public void addRefereeElector(Node elector) {
        if (elector == null) {
            return;
        }   //if
        this.refereeElectedBy.add(elector);
    }

    public int getNumberOfNominations() {
        return numberOfNominations;
    }

    public void incNumberOfNominations() {
        this.numberOfNominations++;
    }
}
