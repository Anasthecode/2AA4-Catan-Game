/**
 * Action for building a road on the board.
 */
public class BuildRoad implements Action {
    private Player player;
    private Board board;
    private EdgePosition edgePosition;

    /**
     * Constructs a BuildRoad action.
     * 
     * @param player the player building the road
     * @param board the game board
     * @param edgePosition the position where the road will be built
     */
    public BuildRoad(Player player, Board board, EdgePosition edgePosition) {
        this.player = player;
        this.board = board;
        this.edgePosition = edgePosition;
    }

    /**
     * Executes the build road action.
     * Checks if the player has the required resources and places the road.
     */
    @Override
    public void execute() {
        // Check if player has required resources (1 wood, 1 brick)
        if (player.removeResource(Resource.WOOD, 1) && 
            player.removeResource(Resource.BRICK, 1)) {
            
            // Create and place the road
            Road road = new Road(player, edgePosition);
            board.placeRoad(road);
            player.addSettlement(road);
            
            System.out.println(player.getName() + " built a road at " + edgePosition);
        } else {
            System.out.println(player.getName() + " does not have enough resources to build a road");
        }
    }
}
