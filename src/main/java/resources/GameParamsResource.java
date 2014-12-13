package resources;

public class GameParamsResource implements Resource {
    private int startCardsCount;
    private int joystickCardsCount;

    public GameParamsResource() {
        setStartCardsCount(7);
        setJoystickCardsCount(3);
    }

    public GameParamsResource(int startCardsCount, int joystickCardsCount) {
        setStartCardsCount(startCardsCount);
        setJoystickCardsCount(joystickCardsCount);
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

