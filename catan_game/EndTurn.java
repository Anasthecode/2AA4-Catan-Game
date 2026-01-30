/**
 * Action for ending the current player's turn.
 */
public class EndTurn implements Action {
    private Game game;
    private Player player;

    /**
     * Constructs an EndTurn action.
     * 
     * @param game the game instance
     * @param player the player ending their turn
     */
    public EndTurn(Game game, Player player) {
        this.game = game;
        this.player = player;
    }

    /**
     * Executes the end turn action.
     * Advances to the next player's turn.
     */
    @Override
    public void execute() {
        System.out.println(player.getName() + " ended their turn");
        // Game will handle advancing to the next player
    }
}
