/**
 * Represents an axial coordinate position on the hexagonal board.
 */
public class AxialPosition {
    private int q;
    private int r;

    /**
     * Constructs an AxialPosition with the specified coordinates.
     * 
     * @param q the q coordinate
     * @param r the r coordinate
     */
    public AxialPosition(int q, int r) {
        this.q = q;
        this.r = r;
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
     * Checks if this position equals another object.
     * 
     * @param obj the object to compare
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AxialPosition that = (AxialPosition) obj;
        return q == that.q && r == that.r;
    }

    /**
     * Returns a hash code for this position.
     * 
     * @return hash code
     */
    @Override
    public int hashCode() {
        return 31 * q + r;
    }
}
