/**
 * Action for building a settlement on the board.
 */
public class BuildSettlement implements Action {
    private Player player;
    private Board board;
    private NodePosition nodePosition;

    /**
     * Constructs a BuildSettlement action.
     * 
     * @param player the player building the settlement
     * @param board the game board
     * @param nodePosition the position where the settlement will be built
     */
    public BuildSettlement(Player player, Board board, NodePosition nodePosition) {
        this.player = player;
        this.board = board;
        this.nodePosition = nodePosition;
    }

    /**
     * Executes the build settlement action.
     * Checks if the player has the required resources and places the settlement.
     */
    @Override
    public void execute() {
        // Check if player has required resources (1 wood, 1 brick, 1 sheep, 1 wheat)
        if (player.removeResource(Resource.WOOD, 1) && 
            player.removeResource(Resource.BRICK, 1) &&
            player.removeResource(Resource.SHEEP, 1) &&
            player.removeResource(Resource.WHEAT, 1)) {
            
            // Create and place the settlement (worth 1 VP)
            Settlement settlement = new Settlement(player, nodePosition, 1);
            board.placeStructure(settlement);
            player.addSettlement(settlement);
            
            System.out.println(player.getName() + " built a settlement at " + nodePosition);
        } else {
            System.out.println(player.getName() + " does not have enough resources to build a settlement");
        }
    }
}
