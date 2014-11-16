package resources;

public class GameParamsResource implements Resource {
    private int startCardsCount;

    public GameParamsResource() {
        setStartCardsCount(7);
    }

    public GameParamsResource(int startCardsCount) {
        setStartCardsCount(startCardsCount);
    }

    public int getStartCardsCount() {
        return startCardsCount;
    }

    public void setStartCardsCount(int startCardsCount) {
        this.startCardsCount = startCardsCount;
    }
}

