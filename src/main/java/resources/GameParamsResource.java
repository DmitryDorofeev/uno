package resources;

public class GameParamsResource implements Resource {
    private int socketTimeOut;
    private int startCardsCount;
    private int joystickCardsCount;

    public GameParamsResource() {
        setSocketTimeOut(10);
        setStartCardsCount(7);
        setJoystickCardsCount(3);
    }

    public GameParamsResource(int socketTimeOut, int startCardsCount, int joystickCardsCount) {
        setSocketTimeOut(socketTimeOut);
        setStartCardsCount(startCardsCount);
        setJoystickCardsCount(joystickCardsCount);
    }

    public int getSocketTimeOut() {
        return socketTimeOut;
    }

    public void setSocketTimeOut(int socketTimeOut) {
        this.socketTimeOut = socketTimeOut;
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

