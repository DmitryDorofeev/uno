package resources;

/**
 * Created by alexey on 15.11.2014.
 */
public class CardResource {
    private int id;
    private String color;
    private int num;
    private int width;
    private int height;
    private int x;
    private int y;

    public CardResource() {
        id = -1;
    }

    public CardResource(int id, String color, int num, int width, int height, int x, int y) {
        setId(id);
        setColor(color);
        setNum(num);
        setWidth(width);
        setHeight(height);
        setX(x);
        setY(y);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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
