package game;

public class Location {
    private int posX;
    private int posY;

    public Location(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public String toString() {
        String str = "X: " + this.posX + "\tY: " + this.posY;
        return str;
    }
}
