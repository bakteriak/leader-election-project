package uk.ac.qub.leaderelectiongame.exceptions;

/**
 * Exception thrown during leader election algorithm.
 */
public class LeaderElectionException extends Exception {

    /**
     * Constructor.
     * @param message
     */
    public LeaderElectionException(String message)
    {
        super(message);
    }
}
