import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract base class representing a player in the game.
 */
public abstract class Player {
    private String playerName;
    private Map<Resource, Integer> inventory;
    private List<Structure> structures;

    /**
     * Constructs a Player with the specified name.
     * 
     * @param name the player's name
     */
    public Player(String name) {
        this.playerName = name;
        this.inventory = new EnumMap<>(Resource.class);
        this.structures = new ArrayList<>();
        
        // Initialize inventory with zero resources
        for (Resource resource : Resource.values()) {
            inventory.put(resource, 0);
        }
    }

    /**
     * Gets the player's name.
     * 
     * @return the name
     */
    public String getName() {
        return playerName;
    }

    /**
     * Gets the player's inventory.
     * 
     * @return the resource inventory map
     */
    public Map<Resource, Integer> getInventory() {
        return new EnumMap<>(inventory);
    }

    /**
     * Adds a resource to the player's inventory.
     * 
     * @param resource the resource to add
     */
    public void addResource(Resource resource) {
        inventory.put(resource, inventory.get(resource) + 1);
    }

    /**
     * Adds a resource with a specific quantity.
     * 
     * @param resource the resource to add
     * @param quantity the amount to add
     */
    public void addResource(Resource resource, int quantity) {
        inventory.put(resource, inventory.get(resource) + quantity);
    }

    /**
     * Removes a resource from the player's inventory.
     * 
     * @param resource the resource to remove
     * @return true if successfully removed, false if not enough resources
     */
    public boolean removeResource(Resource resource) {
        int current = inventory.get(resource);
        if (current > 0) {
            inventory.put(resource, current - 1);
            return true;
        }
        return false;
    }

    /**
     * Removes a specific quantity of a resource.
     * 
     * @param resource the resource to remove
     * @param quantity the amount to remove
     * @return true if successfully removed, false if not enough resources
     */
    public boolean removeResource(Resource resource, int quantity) {
        int current = inventory.get(resource);
        if (current >= quantity) {
            inventory.put(resource, current - quantity);
            return true;
        }
        return false;
    }

    /**
     * Adds a settlement or other structure to the player's collection.
     * 
     * @param settlement the structure to add
     */
    public void addSettlement(Structure settlement) {
        structures.add(settlement);
    }

    /**
     * Gets all structures owned by this player.
     * 
     * @return list of structures
     */
    public List<Structure> getStructures() {
        return new ArrayList<>(structures);
    }

    /**
     * Abstract method for making a move - to be implemented by subclasses.
     */
    public abstract void makeMove();

    /**
     * Returns a string representation of the player.
     * 
     * @return string representation
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Player: ").append(playerName).append("\n");
        sb.append("Resources: ").append(inventory).append("\n");
        sb.append("Structures: ").append(structures.size());
        return sb.toString();
    }
}
