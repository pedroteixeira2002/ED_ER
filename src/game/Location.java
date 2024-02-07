package game;
/**
 * Class for the location
 */
public class Location {
    /** The x position of the location */
    private int posX;
    /** The y position of the location */
    private int posY;

    /**
     * Constructor for the location
     * @param posX
     * @param posY
     */
    public Location(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    /**
     * Getter for the x position
     * @return the x position
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Setter for the x position
     * @param posX
     */
    public void setPosX(int posX) {
        this.posX = posX;
    }

    /**
     * Getter for the y position
     * @return the y position
     */
    public int getPosY() {
        return posY;
    }
    /**
     * Setter for the y position
     * @param posY
     */
    public void setPosY(int posY) {
        this.posY = posY;
    }

    /**
     * To String
     * @return the location
     */
    public String toString() {
        String str = "X: " + this.posX + "\tY: " + this.posY;
        return str;
    }
}
