package mechanics;

/**
 * @author alexey
 */
public class GameUser {
    private final String myName;
    private int myScore = 0;
    private int playersCount;

    public GameUser(String myName, int playersCount) {
        this.myName = myName;
        this.playersCount = playersCount;
    }

    public String getMyName() {
        return myName;
    }

    public int getPlayersCount() { return playersCount; }

    public int getMyScore() {
        return myScore;
    }
}
