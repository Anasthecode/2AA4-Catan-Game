/**
 * Abstract base class for all structures (settlements, cities, roads).
 */
public abstract class Structure {
    private Player owner;

    /**
     * Constructs a Structure with the specified owner.
     * 
     * @param owner the player who owns this structure
     */
    public Structure(Player owner) {
        this.owner = owner;
    }

    /**
     * Gets the owner of this structure.
     * 
     * @return the owning player
     */
    public Player getOwner() {
        return owner;
    }
}
