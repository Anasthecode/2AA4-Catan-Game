/**
 * Abstract base class for settlement-type structures (settlements and cities).
 */
public abstract class SettlementStructure extends Structure {
    private int victoryPoints;

    /**
     * Constructs a SettlementStructure with the specified owner and victory points.
     * 
     * @param owner the player who owns this structure
     * @param victoryPoints the victory point value
     */
    public SettlementStructure(Player owner, int victoryPoints) {
        super(owner);
        this.victoryPoints = victoryPoints;
    }

    /**
     * Gets the victory point value of this structure.
     * 
     * @return the victory points
     */
    public int getVP() {
        return victoryPoints;
    }
}
