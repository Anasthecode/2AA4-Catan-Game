import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Main game class that manages the Catan game flow.
 */
public class Game {
    private int turns;
    private List<Player> players;
    private Board board;
    private Dice[] dice;
    private static final Random rng = new Random();
    private int currentPlayerIndex;

    /**
     * Constructs a Game with the specified parameters.
     * 
     * @param turns the number of turns to play
     * @param board the game board
     * @param players the list of players (3-4 players)
     */
    public Game(int turns, Board board, List<Player> players) {
        this.turns = turns;
        this.board = board;
        this.players = new ArrayList<>(players);
        this.dice = new Dice[2];
        this.dice[0] = new Dice(6);
        this.dice[1] = new Dice(6);
        this.currentPlayerIndex = 0;
    }

    /**
     * Adds a player to the game.
     * 
     * @param player the player to add
     */
    private void addPlayer(Player player) {
        if (players.size() < 4) {
            players.add(player);
        } else {
            System.out.println("Cannot add more than 4 players");
        }
    }

    /**
     * Gets the list of players.
     * 
     * @return array of 3-4 players
     */
    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    /**
     * Gets the game board.
     * 
     * @return the board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Searches for a player by name.
     * 
     * @param name the name to search for
     * @return the player's name if found, null otherwise
     */
    public String searchPlayer(String name) {
        for (Player player : players) {
            if (player.getName().equals(name)) {
                return player.getName();
            }
        }
        return null;
    }

    /**
     * Gets the current player.
     * 
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    /**
     * Advances to the next player's turn.
     */
    public void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    /**
     * Rolls the dice and returns the total.
     * 
     * @return the sum of the two dice
     */
    public int rollDice() {
        int[] rolls = dice[0].rollDice(1);
        int[] rolls2 = dice[1].rollDice(1);
        int total = rolls[0] + rolls2[0];
        System.out.println("Rolled: " + rolls[0] + " + " + rolls2[0] + " = " + total);
        return total;
    }

    /**
     * Plays a single turn of the game.
     */
    public void playTurn() {
        Player currentPlayer = getCurrentPlayer();
        System.out.println("\n" + currentPlayer.getName() + "'s turn");
        
        // Roll dice
        int roll = rollDice();
        
        // Generate resources based on roll
        if (roll != 7) {
            GenerateResources generateResources = new GenerateResources(this, roll);
            generateResources.execute();
        } else {
            System.out.println("Rolled 7! Robber activated (not implemented)");
        }
        
        // Player makes their move
        currentPlayer.makeMove();
        
        // End turn
        EndTurn endTurn = new EndTurn(this, currentPlayer);
        endTurn.execute();
        nextPlayer();
    }

    /**
     * Starts and runs the game.
     */
    public void play() {
        System.out.println("Starting Catan game with " + players.size() + " players");
        System.out.println("Playing for " + turns + " turns");
        
        for (int i = 0; i < turns; i++) {
            System.out.println("\n========== Turn " + (i + 1) + " ==========");
            playTurn();
        }
        
        System.out.println("\nGame over!");
        displayResults();
    }

    /**
     * Displays the final game results.
     */
    private void displayResults() {
        System.out.println("\nFinal Standings:");
        for (Player player : players) {
            int victoryPoints = calculateVictoryPoints(player);
            System.out.println(player.getName() + ": " + victoryPoints + " VP");
        }
    }

    /**
     * Calculates victory points for a player.
     * 
     * @param player the player
     * @return the total victory points
     */
    private int calculateVictoryPoints(Player player) {
        int points = 0;
        for (Structure structure : player.getStructures()) {
            if (structure instanceof SettlementStructure) {
                points += ((SettlementStructure) structure).getVP();
            }
        }
        return points;
    }

    /**
     * Returns a string representation of the game state.
     * 
     * @return string representation
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Catan Game\n");
        sb.append("Turns: ").append(turns).append("\n");
        sb.append("Players: ").append(players.size()).append("\n");
        for (Player player : players) {
            sb.append("  - ").append(player.getName()).append("\n");
        }
        sb.append("Current player: ").append(getCurrentPlayer().getName());
        return sb.toString();
    }
}
