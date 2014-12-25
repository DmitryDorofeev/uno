package resources;

public class GameParamsResource implements Resource {
    private int minPlayersCount;
    private int maxPlayersCount;
    private int startCardsCount;
    private int unoFailCardsCount;
    private int joystickCardsCount;

    public GameParamsResource() {
        setMinPlayersCount(2);
        setMaxPlayersCount(5);
        setStartCardsCount(7);
        setUnoFailCardsCount(2);
        setJoystickCardsCount(3);
    }

    public GameParamsResource(int minPlayersCount, int maxPlayersCount, int startCardsCount,
                              int unoFailCardsCount, int joystickCardsCount) {
        setMinPlayersCount(minPlayersCount);
        setMaxPlayersCount(maxPlayersCount);
        setStartCardsCount(startCardsCount);
        setUnoFailCardsCount(unoFailCardsCount);
        setJoystickCardsCount(joystickCardsCount);
    }

    public int getUnoFailCardsCount() {
        return unoFailCardsCount;
    }

    public void setUnoFailCardsCount(int unoFailCardsCount) {
        this.unoFailCardsCount = unoFailCardsCount;
    }

    public int getMinPlayersCount() {
        return minPlayersCount;
    }

    public void setMinPlayersCount(int minPlayersCount) {
        this.minPlayersCount = minPlayersCount;
    }

    public int getMaxPlayersCount() {
        return maxPlayersCount;
    }

    public void setMaxPlayersCount(int maxPlayersCount) {
        this.maxPlayersCount = maxPlayersCount;
    }

    public int getJoystickCardsCount() {
        return joystickCardsCount;
    }

    public void setJoystickCardsCount(int joystickCardsCount) {
        this.joystickCardsCount = joystickCardsCount;
    }

    public int getStartCardsCount() {
        return startCardsCount;
    }

    public void setStartCardsCount(int startCardsCount) {
        this.startCardsCount = startCardsCount;
    }
}

