import java.util.Map;

/**
 * Represents a computer-controlled player.
 */
public class ComputerPlayer extends Player {

    /**
     * Constructs a ComputerPlayer with the specified name.
     * 
     * @param name the player's name
     */
    public ComputerPlayer(String name) {
        super(name);
    }

    /**
     * Gets the player's name.
     * 
     * @return the name
     */
    @Override
    public String getName() {
        return super.getName();
    }

    /**
     * Gets the player's resource list.
     * 
     * @return the resource inventory
     */
    public Map<Resource, Integer> getResourceList() {
        return getInventory();
    }

    /**
     * Adds a resource to the player's inventory.
     * 
     * @param resource the resource to add
     */
    public void addResource(Resource resource) {
        super.addResource(resource);
    }

    /**
     * Builds a structure.
     * 
     * @param structure the structure to build
     */
    public void build(Structure structure) {
        // Check if player has required resources
        // Deduct resources and add structure
        addSettlement(structure);
    }

    /**
     * Makes a move for the computer player.
     * This implements the AI logic for the computer player.
     */
    @Override
    public void makeMove() {
        // AI logic for computer player would go here
        // This would include:
        // - Analyzing board state
        // - Deciding whether to build roads, settlements, or cities
        // - Trading resources
        // - Playing development cards
        System.out.println(getName() + " is making a move...");
    }

    /**
     * Returns a string representation of the computer player.
     * 
     * @return string representation
     */
    @Override
    public String toString() {
        return "Computer" + super.toString();
    }
}
