import java.util.ArrayList;
import java.util.List;

/**
 * Represents a hexagonal tile on the game board.
 */
public class Tile {
    private AxialPosition position;
    private TileType tileType;
    private boolean blockedByRobber;
    private int numberTokenValue;
    private List<Node> intersections;

    /**
     * Constructs a Tile with the specified properties.
     * 
     * @param position the position of this tile
     * @param token the number token value (2-12, excluding 7)
     * @param type the type of tile
     * @param nodes the 6 nodes at the intersections of this tile
     */
    public Tile(AxialPosition position, int token, TileType type, List<Node> nodes) {
        this.position = position;
        this.numberTokenValue = token;
        this.tileType = type;
        this.blockedByRobber = false;
        this.intersections = new ArrayList<>(nodes);
    }

    /**
     * Gets the type of this tile.
     * 
     * @return the tile type
     */
    public TileType getTileType() {
        return tileType;
    }

    /**
     * Gets the number token value.
     * 
     * @return the token value
     */
    public int getToken() {
        return numberTokenValue;
    }

    /**
     * Sets whether this tile is blocked by the robber.
     * 
     * @param robber true if blocked, false otherwise
     */
    public void setBlockedByRobber(boolean robber) {
        this.blockedByRobber = robber;
    }

    /**
     * Gets whether this tile is blocked by the robber.
     * 
     * @return true if blocked, false otherwise
     */
    public boolean getBlockedByRobber() {
        return blockedByRobber;
    }

    /**
     * Gets the nodes at the intersections of this tile.
     * 
     * @return list of 2-3 nodes (or up to 6)
     */
    public List<Node> getNodes() {
        return new ArrayList<>(intersections);
    }

    /**
     * Gets the position of this tile.
     * 
     * @return the axial position
     */
    public AxialPosition getPosition() {
        return position;
    }

    /**
     * Returns a string representation of this tile.
     * 
     * @return string representation
     */
    @Override
    public String toString() {
        String robberStatus = blockedByRobber ? " [ROBBER]" : "";
        return tileType + " tile (Token: " + numberTokenValue + ")" + robberStatus;
    }
}
