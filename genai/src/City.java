/**
 * Represents a city structure that can be placed on a node (upgrading a settlement).
 */
public class City extends SettlementStructure {
    private NodePosition location;

    /**
     * Constructs a City at the specified location with victory points.
     * 
     * @param owner the player who owns this city
     * @param location the location on the board
     * @param VP the victory point value
     */
    public City(Player owner, NodePosition location, int VP) {
        super(owner, VP);
        this.location = location;
    }

    /**
     * Gets the location of this city.
     * 
     * @return the node position
     */
    public NodePosition getLocation() {
        return location;
    }

    /**
     * Returns a string representation of this city.
     * 
     * @return string representation
     */
    @Override
    public String toString() {
        return "City (VP: " + getVP() + ") owned by " + getOwner().getName();
    }
}
