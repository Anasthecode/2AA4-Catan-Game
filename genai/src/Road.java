/**
 * Represents a road structure that can be placed on an edge.
 */
public class Road extends Structure {
    private EdgePosition location;

    /**
     * Constructs a Road owned by the specified player.
     * 
     * @param owner the player who owns this road
     * @param location the location on the board
     */
    public Road(Player owner, EdgePosition location) {
        super(owner);
        this.location = location;
    }

    /**
     * Gets the location of this road.
     * 
     * @return the edge position
     */
    public EdgePosition getLocation() {
        return location;
    }

    /**
     * Returns a string representation of this road.
     * 
     * @return string representation
     */
    @Override
    public String toString() {
        return "Road owned by " + getOwner().getName();
    }
}
