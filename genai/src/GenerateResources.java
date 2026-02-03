import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Action for generating resources based on dice roll.
 */
public class GenerateResources implements Action {
    private Game game;
    private int diceRoll;

    /**
     * Constructs a GenerateResources action.
     * 
     * @param game the game instance
     * @param diceRoll the sum of the dice roll
     */
    public GenerateResources(Game game, int diceRoll) {
        this.game = game;
        this.diceRoll = diceRoll;
    }

    /**
     * Executes the resource generation.
     * Distributes resources to players based on their settlements/cities on tiles matching the dice roll.
     */
    @Override
    public void execute() {
        Board board = game.getBoard();
        List<Tile> tiles = board.getTiles();
        
        // Map to convert TileType to Resource
        Map<TileType, Resource> tileToResource = new HashMap<>();
        tileToResource.put(TileType.FOREST, Resource.WOOD);
        tileToResource.put(TileType.PASTURE, Resource.SHEEP);
        tileToResource.put(TileType.FIELD, Resource.WHEAT);
        tileToResource.put(TileType.HILLS, Resource.BRICK);
        tileToResource.put(TileType.MOUNTAIN, Resource.ORE);
        
        // For each tile matching the dice roll
        for (Tile tile : tiles) {
            if (tile.getToken() == diceRoll && !tile.getBlockedByRobber()) {
                Resource resource = tileToResource.get(tile.getTileType());
                
                if (resource != null) {
                    // Check each node on this tile
                    for (Node node : tile.getNodes()) {
                        SettlementStructure structure = node.getStructure();
                        if (structure != null) {
                            Player owner = structure.getOwner();
                            
                            // Settlements generate 1 resource, cities generate 2
                            int amount = structure instanceof City ? 2 : 1;
                            
                            for (int i = 0; i < amount; i++) {
                                owner.addResource(resource);
                            }
                            
                            System.out.println(owner.getName() + " received " + amount + " " + resource);
                        }
                    }
                }
            }
        }
    }
}
