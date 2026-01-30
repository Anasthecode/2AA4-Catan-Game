import java.util.ArrayList;
import java.util.List;

/**
 * Demonstrator class to show the Catan game in action.
 */
public class Demonstrator {
    
    /**
     * Main method to run a demonstration of the Catan game.
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("=== Catan Game Demonstrator ===\n");
        
        // Create a board
        Board board = new Board();
        System.out.println("Created game board");
        
        // Create players
        List<Player> players = new ArrayList<>();
        players.add(new ComputerPlayer("Alice"));
        players.add(new ComputerPlayer("Bob"));
        players.add(new ComputerPlayer("Charlie"));
        System.out.println("Created 3 players: Alice, Bob, and Charlie\n");
        
        // Give players some starting resources for demonstration
        for (Player player : players) {
            player.addResource(Resource.WOOD, 2);
            player.addResource(Resource.BRICK, 2);
            player.addResource(Resource.SHEEP, 2);
            player.addResource(Resource.WHEAT, 2);
            player.addResource(Resource.ORE, 2);
        }
        System.out.println("Distributed starting resources to all players\n");
        
        // Create and start the game
        Game game = new Game(5, board, players);
        
        System.out.println(game.toString());
        System.out.println("\n" + "=".repeat(50));
        
        // Play the game
        game.play();
        
        System.out.println("\n=== Demonstration Complete ===");
    }
}
