package resources;

/**
 * Created by alexey on 15.11.2014.
 */
public class CardResource {
    private long cardId;
    private String color;
    private String type;
    private int num;
    private int width;
    private int height;
    private int x;
    private int y;

    public CardResource() {
        setCardId(0);
        setColor("red");
        setType("number");
        setNum(0);
        setWidth(240);
        setHeight(360);
        setX(0);
        setY(0);
    }

    public CardResource(long cardId, String color, String type, int num, int width, int height, int x, int y) {
        setCardId(cardId);
        setColor(color);
        setType(type);
        setNum(num);
        setWidth(width);
        setHeight(height);
        setX(x);
        setY(y);
    }

    public long getCardId() {
        return cardId;
    }

    public void setCardId(long cardId) {
        this.cardId = cardId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
