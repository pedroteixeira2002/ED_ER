package game;

public class Flag {
    /**
     * Location of the flag
     */
    private Location location;

    public Flag(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
