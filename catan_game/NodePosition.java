/**
 * Represents the position of a node on the board using axial coordinates.
 */
public class NodePosition {
    private int q;
    private int r;
    private RelativeNodeLocation location;

    /**
     * Constructs a NodePosition with the specified coordinates and relative location.
     * 
     * @param q the q coordinate
     * @param r the r coordinate
     * @param location the relative location on the tile
     */
    public NodePosition(int q, int r, RelativeNodeLocation location) {
        this.q = q;
        this.r = r;
        this.location = location;
    }

    /**
     * Gets the q coordinate.
     * 
     * @return the q coordinate
     */
    public int getQ() {
        return q;
    }

    /**
     * Gets the r coordinate.
     * 
     * @return the r coordinate
     */
    public int getR() {
        return r;
    }

    /**
     * Gets the relative location on the tile.
     * 
     * @return the relative node location
     */
    public RelativeNodeLocation getRelativeLocation() {
        return location;
    }

    /**
     * Checks if this position equals another object.
     * 
     * @param obj the object to compare
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        NodePosition that = (NodePosition) obj;
        return q == that.q && r == that.r && location == that.location;
    }

    /**
     * Returns a hash code for this position.
     * 
     * @return hash code
     */
    @Override
    public int hashCode() {
        int result = 31 * q + r;
        result = 31 * result + (location != null ? location.hashCode() : 0);
        return result;
    }
}
