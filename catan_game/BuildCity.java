/**
 * Action for building a city on the board (upgrading a settlement).
 */
public class BuildCity implements Action {
    private Player player;
    private Board board;
    private NodePosition nodePosition;

    /**
     * Constructs a BuildCity action.
     * 
     * @param player the player building the city
     * @param board the game board
     * @param nodePosition the position where the city will be built
     */
    public BuildCity(Player player, Board board, NodePosition nodePosition) {
        this.player = player;
        this.board = board;
        this.nodePosition = nodePosition;
    }

    /**
     * Executes the build city action.
     * Checks if the player has the required resources and upgrades the settlement to a city.
     */
    @Override
    public void execute() {
        // Check if player has required resources (3 ore, 2 wheat)
        if (player.removeResource(Resource.ORE, 3) && 
            player.removeResource(Resource.WHEAT, 2)) {
            
            // Create and place the city (worth 2 VP)
            City city = new City(player, nodePosition, 2);
            board.placeStructure(city);
            player.addSettlement(city);
            
            System.out.println(player.getName() + " built a city at " + nodePosition);
        } else {
            System.out.println(player.getName() + " does not have enough resources to build a city");
        }
    }
}
