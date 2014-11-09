package mechanics;

/**
 * @author alexey
 */
public class GameUser {
    private final String myName;
    private int myScore = 0;
    private long playersCount;

    public GameUser(String myName, long playersCount) {
        this.myName = myName;
        this.playersCount = playersCount;
    }

    public String getMyName() {
        return myName;
    }

    public long getPlayersCount() { return playersCount; }

    public int getMyScore() {
        return myScore;
    }
}
