/**
 * Represents a settlement structure that can be placed on a node.
 */
public class Settlement extends SettlementStructure {
    private NodePosition location;

    /**
     * Constructs a Settlement at the specified location with victory points.
     * 
     * @param owner the player who owns this settlement
     * @param location the location on the board
     * @param VP the victory point value
     */
    public Settlement(Player owner, NodePosition location, int VP) {
        super(owner, VP);
        this.location = location;
    }

    /**
     * Gets the location of this settlement.
     * 
     * @return the node position
     */
    public NodePosition getLocation() {
        return location;
    }

    /**
     * Returns a string representation of this settlement.
     * 
     * @return string representation
     */
    @Override
    public String toString() {
        return "Settlement (VP: " + getVP() + ") owned by " + getOwner().getName();
    }
}
